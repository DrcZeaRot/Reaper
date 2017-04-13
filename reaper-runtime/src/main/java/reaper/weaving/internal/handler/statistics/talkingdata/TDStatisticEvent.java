package reaper.weaving.internal.handler.statistics.talkingdata;

import reaper.weaving.internal.handler.statistics.StatisticEvent;
import reaper.weaving.statistics.EventType;

/**
 * Created by R on 2017/3/29.
 */

public class TDStatisticEvent extends StatisticEvent {
    private long startTime;

    private EventType type;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TDStatisticEvent{" +
                "pageName=" + pageName +
                "actionName" + actionName +
                "startTime=" + startTime +
                ", type=" + type +
                "} ";
    }
}
