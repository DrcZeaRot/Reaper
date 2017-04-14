package reaper.weaving.statistics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by R on 2017/3/29.
 * <p>标记Activity/Fragment，用于统计页面停留
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface PageDuration {
    /**
     * 必填 页面信息
     */
    String pageName();

    Class<?> clazz();
}
