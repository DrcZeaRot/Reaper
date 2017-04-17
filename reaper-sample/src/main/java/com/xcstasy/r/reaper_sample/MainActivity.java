package com.xcstasy.r.reaper_sample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import reaper.weaving.statistics.EventType;
import reaper.weaving.statistics.OnEvent;
import reaper.weaving.statistics.PageDuration;

@PageDuration(pageName = "Main")
public class MainActivity extends AppCompatActivity {

    private static final long startTime = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StatisticReaper.setEnabled(true);

        MainFragment mainFragment = new MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.flContent, mainFragment).commitAllowingStateLoss();

        onEvent();
        onStartTime(System.currentTimeMillis());
    }

    @OnEvent(event = EventType.PAGE_ACTION, pageName = "MainActivity", actionName = "onEvent", index = 1)
    private void onEvent() {
//        Log.e("RRR","MainOnEvent");
    }

    @OnEvent(event = EventType.PAGE_DURATION, pageName = "MainActivity", actionName = "onStartTime", index = 2)
    private void onStartTime(Long startTime) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
