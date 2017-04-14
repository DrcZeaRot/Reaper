package reaper.weaving.internal;

import reaper.weaving.internal.handler.statistics.StatisticEvent;
import reaper.weaving.internal.handler.statistics.StatisticHandlerStrategy;
import reaper.weaving.internal.handler.statistics.talkingdata.TDStaticHandlerStrategy;

/**
 * Created by R on 2017/4/13.
 */

public class StatisticProvider {

    private static volatile StatisticProvider INSTANCE;

    private StatisticHandlerStrategy<? extends StatisticEvent> strategy;

    private StatisticProvider() {
        setStrategy(new TDStaticHandlerStrategy());
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

    public void setStrategy(StatisticHandlerStrategy<? extends StatisticEvent> strategy) {
        this.strategy = strategy;
    }

    public StatisticHandlerStrategy<StatisticEvent> getStrategy() {
        return (StatisticHandlerStrategy<StatisticEvent>) strategy;
    }
}
