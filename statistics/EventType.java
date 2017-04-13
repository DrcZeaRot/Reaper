package reaper.weaving.annotations.statistics;

/**
 * Created by R on 2017/3/28.
 * <p>描述事件类型
 */

public enum EventType {

    /**
     * 普通Action事件
     */
    ACTION,
    /**
     * page+action事件
     */
    PAGE_ACTION,
    /**
     * 计时事件
     */
    PAGE_DURATION
}
