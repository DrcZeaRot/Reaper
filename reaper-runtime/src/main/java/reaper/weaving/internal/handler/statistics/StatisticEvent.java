package reaper.weaving.internal.handler.statistics;

import reaper.weaving.statistics.EventType;

/**
 * Created by R on 2017/3/29.
 */

public class StatisticEvent {
    protected String pageName;
    protected String actionName;
    protected EventType type;
    protected int index;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
