package reaper.weaving.internal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import reaper.weaving.internal.handler.statistics.StatisticEvent;
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
    //android.content.Context,reaper.weaving.internal.handler.statistics.StatisticEvent+,..

    private final String POINT_CUT_ONEVENT =
            "execution(" +
                    "@reaper.weaving.statistics.OnEvent * *" +
                    "(..))" +
                    "&& @annotation(onEvent)";

    private final String POINT_CUT_ONEVENT_START_TIME =
            "execution(" +
                    "@reaper.weaving.statistics.OnEvent * *" +
                    "(java.lang.Long,..))" +
                    "&& @annotation(onEvent)";
    private final String POINT_CUT_ONTYPE =
            "within(@reaper.weaving.statistics.PageDuration *)";

    private StatisticHandler<StatisticEvent> handler = new StatisticHandler(StatisticProvider.getInstance().getStrategy());

    public StatisticReaper() {

    }

    private static volatile boolean enabled = true;

    @Pointcut(POINT_CUT_ONEVENT)
    public void withinMethodAnnotated(OnEvent onEvent) {
    }

    @Pointcut(POINT_CUT_ONEVENT_START_TIME)
    public void withinStartTimeMethodAnnotated(OnEvent onEvent) {

    }

    @Pointcut(POINT_CUT_ONTYPE)
    public void withinClassAnnotated() {
    }

    @Pointcut("this(android.app.Activity+) && execution(* *.onCreate(android.os.Bundle))")
    public void activityOnCreate() {
    }

    @Pointcut("this(android.app.Activity+) && execution(* *.onDestroy())")
    public void activityOnDestroy() {
    }

    @Pointcut("this(android.support.v4.app.Fragment+) && execution(* *.onViewCreated(android.view.View,android.os.Bundle))")
    public void fragmentOnCreateView() {
    }

    @Pointcut("this(android.support.v4.app.Fragment+) && execution(* *.onDestroyView())")
    public void fragmentOnDestroyView() {
    }


    @Around("withinMethodAnnotated(onEvent)")
    public Object onEvent(ProceedingJoinPoint joinPoint, OnEvent onEvent) throws Throwable {
        markEvent(joinPoint, onEvent);
        return joinPoint.proceed();
    }

    @Around("withinStartTimeMethodAnnotated(onEvent)")
    public Object onStartTimeEvent(ProceedingJoinPoint joinPoint, OnEvent onEvent) throws Throwable {
        markStartTimeEvent(joinPoint, onEvent);
        return joinPoint.proceed();
    }

    @Around("withinClassAnnotated() && activityOnCreate()")
    public Object onActivityCreated(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        markDuration(joinPoint, EventType.PAGE_START);
        return result;
    }

    @Around("withinClassAnnotated() && activityOnDestroy()")
    public Object onActivityDestroy(ProceedingJoinPoint joinPoint) throws Throwable {
        markDuration(joinPoint, EventType.PAGE_END);
        return joinPoint.proceed();
    }

    //    @Around("withinClassAnnotated(duration) && fragmentOnCreateView()")
    @Around("withinClassAnnotated() && fragmentOnCreateView()")
    public Object onFragmentCreateView(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        markDuration(joinPoint, EventType.PAGE_START);
        return result;
    }

    //    @Around("withinClassAnnotated(duration) && fragmentOnDestroyView()")
    @Around("withinClassAnnotated() && fragmentOnDestroyView()")
    public Object onFragmentDestroyView(ProceedingJoinPoint joinPoint) throws Throwable {
        markDuration(joinPoint, EventType.PAGE_END);
        return joinPoint.proceed();
    }

    public static void setEnabled(boolean enabled) {
        StatisticReaper.enabled = enabled;
    }

    private void markEvent(JoinPoint joinPoint, OnEvent onEvent) throws IllegalAccessException, InstantiationException {
        if (!enabled) return;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0 && args[0] instanceof Long) {
            return;
        }
        TDStatisticEvent event = new TDStatisticEvent();
        event.setIndex(onEvent.index());
        event.setType(onEvent.event());
        event.setActionName(onEvent.actionName());
        event.setPageName(onEvent.pageName());
        handler.onEvent(StatisticProvider.getInstance().getContext(), event);
    }

    private void markStartTimeEvent(ProceedingJoinPoint joinPoint, OnEvent onEvent) {
        if (!enabled) return;
        Object[] args = joinPoint.getArgs();
        long startTime = (long) args[0];
        TDStatisticEvent event = new TDStatisticEvent();
        event.setStartTime(startTime);
        event.setIndex(onEvent.index());
        event.setType(onEvent.event());
        event.setActionName(onEvent.actionName());
        event.setPageName(onEvent.pageName());
        handler.onEvent(StatisticProvider.getInstance().getContext(), event);
    }

    private void markDuration(JoinPoint joinPoint, EventType eventType) throws IllegalAccessException, InstantiationException {
        if (!enabled) return;
        Object aThis = joinPoint.getThis();
        PageDuration duration = aThis.getClass().getAnnotation(PageDuration.class);
        Class<?> clazz = StatisticProvider.getInstance().getEventClass();
        StatisticEvent event = (StatisticEvent) clazz.newInstance();
        event.setPageName(duration.pageName());
        event.setType(eventType);
        handler.onEvent(StatisticProvider.getInstance().getContext(), event);
    }


}
