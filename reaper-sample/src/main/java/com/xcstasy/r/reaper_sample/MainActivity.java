package com.xcstasy.r.reaper_sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import reaper.weaving.internal.handler.statistics.talkingdata.TDStatisticEvent;
import reaper.weaving.statistics.EventType;
import reaper.weaving.statistics.OnEvent;
import reaper.weaving.statistics.PageDuration;

@PageDuration(pageName = "Main", clazz = TDStatisticEvent.class)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StatisticReaper.setEnabled(true);


        TDStatisticEvent event = new TDStatisticEvent();
        event.setStartTime(System.currentTimeMillis());
        event.setPageName("MainActivity");
        event.setActionName("onEvent");
        event.setIndex(0);
        event.setType(EventType.PAGE_INDEX_ACTION);
        onEvent(this, event);

    }

    @OnEvent
    private void onEvent(Context context, TDStatisticEvent event) {
//        Log.e("RRR","MainOnEvent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
