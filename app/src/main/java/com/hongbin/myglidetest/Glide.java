package com.hongbin.myglidetest;

import android.app.Activity;

import com.hongbin.myglidetest.manager.RequestManager;
import com.hongbin.myglidetest.manager.RequestManagerRetriever;

/**
 * Created by HongBin on 2019-10-24.
 * ●█
 * 〓██▄▄▄▄▄▄ “突突突，哒哒哒”
 * ●●●●●●
 * ▄▅██████▅▄▃▂
 * ██████████████
 * ◥⊙▲⊙▲⊙▲⊙▲⊙▲⊙▲◤
 *
 * 写一个简单的Glide，基本功能都具备，
 */
public class Glide {

    private RequestManagerRetriever requestManagerRetriever;

    private static Glide glide;

    public RequestManager with(Activity activity){
        return init().getRequestManagerRetriever().getRequestManager(activity);
    }

    private static Glide init(){
        if(glide == null){
            synchronized (Glide.class){
                if(glide == null){
                    glide = new Glide();
                }
            }
        }
        return glide;
    }


    private Glide() {
        requestManagerRetriever = new RequestManagerRetriever();
    }

    private RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }
}
