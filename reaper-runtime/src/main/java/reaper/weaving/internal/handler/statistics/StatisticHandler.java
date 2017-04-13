package reaper.weaving.internal.handler.statistics;

import android.content.Context;

/**
 * Created by R on 2017/3/29.
 */

public class StatisticHandler<E extends StatisticEvent> {

    private StatisticHandlerStrategy<E> mStrategy;

    public StatisticHandler(StatisticHandlerStrategy<E> strategy) {
        setStatisticStrategy(strategy);
    }

    public void onEvent(Context context, E event) {
        mStrategy.onEvent(context, event);
    }

    public void setStatisticStrategy(StatisticHandlerStrategy<E> strategy) {
        mStrategy = strategy;
    }

}
