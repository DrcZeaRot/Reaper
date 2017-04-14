package reaper.weaving.statistics;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by R on 2017/3/28.
 * 修饰method，用于统计事件
 */
@Target({METHOD})
@Retention(CLASS)
public @interface OnEvent {
//    /**
//     * 事件类型
//     */
//    EventType event() default EventType.ACTION;
//
//    /**
//     * pageName
//     */
//    String pageName() default "";
//
//    /**
//     * actionName
//     */
//    String actionName() default "";
//
//    /**
//     * action index
//     */
//    int index() default 0;
//
//    /**
//     * 起始时间 只有PAGE_DURATION需要
//     */
//    long startTime() default 0;
}
