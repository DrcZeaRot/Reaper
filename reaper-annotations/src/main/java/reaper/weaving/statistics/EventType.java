package reaper.weaving.statistics;

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
     * 列表Action事件
     */
    INDEX_ACTION,
    /**
     * page+action事件
     */
    PAGE_ACTION,
    /**
     * page+列表action事件
     */
    PAGE_INDEX_ACTION,
    /**
     * 计时事件
     */
    PAGE_DURATION,

    /**
     * 页面开始
     */
    PAGE_START,
    /**
     * 页面结束
     */
    PAGE_END
}
