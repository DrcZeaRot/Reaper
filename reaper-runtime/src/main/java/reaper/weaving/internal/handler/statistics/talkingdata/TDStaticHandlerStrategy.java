package reaper.weaving.internal.handler.statistics.talkingdata;

import android.content.Context;
import android.util.Log;

import reaper.weaving.internal.handler.statistics.StatisticHandlerStrategy;

/**
 * Created by R on 2017/3/29.
 */

public class TDStaticHandlerStrategy implements StatisticHandlerStrategy<TDStatisticEvent> {

    @Override
    public void onEvent(Context context, TDStatisticEvent event) {
//        String typeStr = "";
//        EventType type = event.getType();
//        switch (type) {
//            case ACTION:
//                typeStr = "ACTION";
//                break;
//            case PAGE_ACTION:
//                typeStr = "PAGE_ACTION";
//                break;
//            case PAGE_DURATION:
//                typeStr = "PAGE_DURATION";
//                break;
//            case PAGE_START:
//                typeStr = "PAGE_START";
//                break;
//            case PAGE_END:
//                typeStr = "PAGE_END";
//                break;
//        }

        Log.e("RRR", event.toString());


    }

}
