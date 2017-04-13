package reaper.weaving.internal.handler.statistics;

import android.content.Context;

/**
 * Created by R on 2017/3/29.
 */

public interface StatisticHandlerStrategy<E extends StatisticEvent> {
    /**
     * 点击事件、计算时长事件
     * @param context
     * @param event
     */
    void onEvent(Context context, E event);
}
