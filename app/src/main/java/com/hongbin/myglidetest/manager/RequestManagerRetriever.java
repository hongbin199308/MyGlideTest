package com.hongbin.myglidetest.manager;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;

import com.hongbin.myglidetest.lifecycle.SupportLifecycleFragment;

/**
 * Created by HongBin on 2019-10-25.
 * ●█
 * 〓██▄▄▄▄▄▄ “突突突，哒哒哒”
 * ●●●●●●
 * ▄▅██████▅▄▃▂
 * ██████████████
 * ◥⊙▲⊙▲⊙▲⊙▲⊙▲⊙▲◤
 * 简化版，只支持Activity
 */
public class RequestManagerRetriever implements Handler.Callback{

    //每个Activity都只能有一个SupportFragment来监听生命周期，
    static final String FRAGMENT_TAG = "MY_FRAGMENT_TAG";
    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;

    //处理并发创建SupportFragment的问题（但是这个项目只能在主线程使用，不会有并发，就当练手了）
    private final ArrayMap<FragmentManager, SupportLifecycleFragment> supportFragmentArrayMap = new ArrayMap<>();

    private final Handler handler;

    public RequestManagerRetriever() {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    public RequestManager getRequestManager(Activity activity){
        FragmentManager fragmentManager = activity.getFragmentManager();
        SupportLifecycleFragment fragment = getFragment(fragmentManager);


        return new RequestManager(fragment);
    }

    private SupportLifecycleFragment getFragment(FragmentManager fragmentManager) {
        SupportLifecycleFragment fragment = (SupportLifecycleFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if(fragment == null){
            fragment = supportFragmentArrayMap.get(fragmentManager);
            //如果Map中也没有，说明是真的没有在这个Activity中添加过SupportFragment;
            if(fragment == null){
                fragment = new SupportLifecycleFragment();
                supportFragmentArrayMap.put(fragmentManager, fragment);
                fragmentManager.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
                //成功将SupportFragment添加到Activity后要移除supportFragmentArrayMap中的SupportFragment
                //fragmentManager开启事务的实质就是往looper中丢一个事件，处理完后会执行ID_REMOVE_FRAGMENT_MANAGER事件
                handler.obtainMessage(ID_REMOVE_FRAGMENT_MANAGER, fragment).sendToTarget();
            }
        }
        return fragment;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if(msg.what == ID_REMOVE_FRAGMENT_MANAGER){
            supportFragmentArrayMap.remove((FragmentManager) msg.obj);
            return true;
        }
        return false;
    }
}
