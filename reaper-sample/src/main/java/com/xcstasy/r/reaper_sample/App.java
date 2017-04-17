package com.xcstasy.r.reaper_sample;

import android.app.Application;

import reaper.weaving.internal.StatisticProvider;

/**
 * Created by R on 2017/4/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StatisticProvider.getInstance().init(this);
    }
}
