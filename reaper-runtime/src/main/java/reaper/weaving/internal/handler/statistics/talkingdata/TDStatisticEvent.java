package reaper.weaving.internal.handler.statistics.talkingdata;

import reaper.weaving.internal.handler.statistics.StatisticEvent;

/**
 * Created by R on 2017/3/29.
 */

public class TDStatisticEvent extends StatisticEvent {
    private long startTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "TDStatisticEvent{" +
                ", pageName=" + pageName +
                ", actionName=" + actionName +
                ", type=" + type +
                ", index=" + index +
                ", startTime=" + startTime +
                "} ";
    }
}
