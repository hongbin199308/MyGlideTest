package com.hongbin.myglidetest.lifecycle;

import android.app.Fragment;
import android.os.Bundle;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by HongBin on 2019-10-25.
 * ●█
 * 〓██▄▄▄▄▄▄ “突突突，哒哒哒”
 * ●●●●●●
 * ▄▅██████▅▄▃▂
 * ██████████████
 * ◥⊙▲⊙▲⊙▲⊙▲⊙▲⊙▲◤
 * 负责生命周期方法回调
 */
public class SupportLifecycleFragment extends Fragment implements LifeCycle{

    private final Set<LifecycleListener> lifecycleListeners =
            Collections.newSetFromMap(new WeakHashMap<LifecycleListener, Boolean>());

    private boolean isStarted;
    private boolean isDestroyed;

    @Override
    public void addListener(LifecycleListener listener) {
        lifecycleListeners.add(listener);

        if (isDestroyed) {
            listener.onDestroy();
        } else if (isStarted) {
            listener.onStart();
        } else {
            listener.onStop();
        }
    }

    @Override
    public void removeListener(LifecycleListener listener) {
        lifecycleListeners.remove(listener);
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        for(LifecycleListener listener:lifecycleListeners){
            listener.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
        for(LifecycleListener listener:lifecycleListeners){
            listener.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        for(LifecycleListener listener:lifecycleListeners){
            listener.onDestroy();
        }
    }
}
