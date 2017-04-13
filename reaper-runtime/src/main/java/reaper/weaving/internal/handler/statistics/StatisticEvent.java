package reaper.weaving.internal.handler.statistics;

/**
 * Created by R on 2017/3/29.
 */

public class StatisticEvent {
    protected String pageName;
    protected String actionName;

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

}
