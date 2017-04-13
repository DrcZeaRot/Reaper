package reaper.weaving.internal.handler;

import reaper.weaving.internal.handler.statistics.StatisticHandler;
import reaper.weaving.internal.handler.statistics.talkingdata.TDStaticHandlerStrategy;
import reaper.weaving.internal.handler.statistics.talkingdata.TDStatisticEvent;

/**
 * Created by R on 2017/4/13.
 */

public class StatisticProvider {

    public static StatisticHandler<TDStatisticEvent> defaultHander() {
        return new StatisticHandler<>(new TDStaticHandlerStrategy());
    }
}
