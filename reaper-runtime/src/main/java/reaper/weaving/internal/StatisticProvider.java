package reaper.weaving.internal;

import android.content.Context;

import reaper.weaving.internal.handler.statistics.StatisticEvent;
import reaper.weaving.internal.handler.statistics.StatisticHandlerStrategy;
import reaper.weaving.internal.handler.statistics.talkingdata.TDStaticHandlerStrategy;
import reaper.weaving.internal.handler.statistics.talkingdata.TDStatisticEvent;

/**
 * Created by R on 2017/4/13.
 */

public class StatisticProvider {

    private static volatile StatisticProvider INSTANCE;
    private Context mContext;

    private StatisticHandlerStrategy<? extends StatisticEvent> strategy;
    private Class<? extends StatisticEvent> eventClass;

    private StatisticProvider() {
    }

    public static StatisticProvider getInstance() {
        StatisticProvider instance = INSTANCE;
        if (instance == null) {
            synchronized (StatisticProvider.class) {
                instance = INSTANCE;
                if (instance == null) {
                    INSTANCE = new StatisticProvider();
                    instance = INSTANCE;
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        init(context, new TDStaticHandlerStrategy(), TDStatisticEvent.class);
    }

    public void init(Context context, StatisticHandlerStrategy<? extends StatisticEvent> strategy, Class<? extends StatisticEvent> eventClass) {
        mContext = context.getApplicationContext();
        this.strategy = strategy;
        this.eventClass = eventClass;
    }

    public StatisticHandlerStrategy<StatisticEvent> getStrategy() {
        checkNull(strategy);
        return (StatisticHandlerStrategy<StatisticEvent>) strategy;
    }

    public Class<? extends StatisticEvent> getEventClass() {
        checkNull(eventClass);
        return eventClass;
    }

    public Context getContext() {
        checkNull(mContext);
        return mContext;
    }

    private void checkNull(Object object) {
        if (object == null) {
            throw new IllegalStateException("call init() before use");
        }
    }

}
