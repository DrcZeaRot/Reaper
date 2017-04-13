package reaper.weaving.internal;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import reaper.weaving.internal.handler.StatisticProvider;
import reaper.weaving.internal.handler.statistics.StatisticHandler;
import reaper.weaving.internal.handler.statistics.talkingdata.TDStatisticEvent;
import reaper.weaving.statistics.EventType;
import reaper.weaving.statistics.OnEvent;
import reaper.weaving.statistics.PageDuration;

/**
 * Created by R on 2017/3/29.
 */
@Aspect
public class StatisticReaper {

    private StatisticHandler<TDStatisticEvent> handler = StatisticProvider.defaultHander();

    private static volatile boolean enabled = true;

    @Pointcut("execution(@reaper.weaving.statistics.OnEvent * *(android.content.Context,..))&& @annotation(onEvent) && args(context,..)")
    public void withinMethodAnnotated() {
    }

    @Pointcut("within(@reaper.weaving.statistics.PageDuration *)&& @annotation(duration)")
    public void withinClassAnnotated() {
    }


    @Pointcut("execution(protected VOID android.app.Activity+.onCreate(android.os.Bundle)) && target(activity) ")
    public void activityOnCreate() {
    }

    @Pointcut("execution(protected VOID android.app.Activity+.onDestroy()) && target(activity)")

    public void activityOnDestroy() {
    }

    @Pointcut("execution(protected VOID android.support.v4.app.Fragment+.onViewCreated(android.view.View,android.os.Bundle)) && target(fragment)")
    public void fragmentOnCreateView() {
    }

    @Pointcut("execution(protected VOID android.support.v4.app.Fragment+.onDestroyView()) && target(fragment)")
    public void fragmentOnDestroyView() {
    }

    @Before("withinMethodAnnotated()")
    public void onEvent(ProceedingJoinPoint joinPoint, OnEvent onEvent, Context context) {
        if (enabled) {
            TDStatisticEvent event = new TDStatisticEvent();
            event.setType(onEvent.event());
            event.setPageName(onEvent.pageName());
            event.setActionName(onEvent.actionName());
            event.setStartTime(onEvent.startTime());
            handler.onEvent(context, event);
        }
    }

    @After("withinClassAnnotated() && activityOnCreate()")
    public void onActivityCreated(ProceedingJoinPoint joinPoint, PageDuration duration, Activity activity) {
        if (enabled) {
            TDStatisticEvent event = new TDStatisticEvent();
            event.setPageName(duration.value());
            event.setType(EventType.PAGE_START);
            handler.onEvent(activity, event);
        }
    }

    @Before("withinClassAnnotated() && activityOnDestroy()")
    public void onActivityDestroy(ProceedingJoinPoint joinPoint, PageDuration duration, Activity activity) {
        if (enabled) {
            TDStatisticEvent event = new TDStatisticEvent();
            event.setPageName(duration.value());
            event.setType(EventType.PAGE_END);
            handler.onEvent(activity, event);
        }
    }

    @After("withinClassAnnotated() && fragmentOnCreateView()")
    public void onFragmentCreateView(ProceedingJoinPoint joinPoint, PageDuration duration, Fragment fragment) {
        if (enabled) {
            TDStatisticEvent event = new TDStatisticEvent();
            event.setPageName(duration.value());
            event.setType(EventType.PAGE_START);
            handler.onEvent(fragment.getContext(), event);
        }
    }

    @Before("withinClassAnnotated() && fragmentOnDestroyView()")
    public void onFragmentDestroyView(ProceedingJoinPoint joinPoint, PageDuration duration, Fragment fragment) {
        if (enabled) {
            TDStatisticEvent event = new TDStatisticEvent();
            event.setPageName(duration.value());
            event.setType(EventType.PAGE_END);
            handler.onEvent(fragment.getContext(), event);
        }
    }

    public static void setEnabled(boolean enabled) {
        StatisticReaper.enabled = enabled;
    }


}
