package com.jni.main.recyclerviewdemo;

import android.app.Application;

import com.gw.swipeback.tools.WxSwipeBackActivityManager;

/**
 * Created by admin on 2017/12/20.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WxSwipeBackActivityManager.getInstance().init(this);
    }
}
