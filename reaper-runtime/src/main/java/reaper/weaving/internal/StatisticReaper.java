package reaper.weaving.internal;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import reaper.weaving.internal.handler.statistics.StatisticEvent;
import reaper.weaving.internal.handler.statistics.StatisticHandler;
import reaper.weaving.statistics.EventType;
import reaper.weaving.statistics.PageDuration;

/**
 * Created by R on 2017/3/29.
 */
@Aspect
public class StatisticReaper {

    private final String POINT_CUT_ONEVENT =
            "execution(" +
                    "@reaper.weaving.statistics.OnEvent * *" +
                    "(android.content.Context,reaper.weaving.internal.handler.statistics.StatisticEvent+,..)" +
                    ")";
    private final String POINT_CUT_ONTYPE =
            "within(@reaper.weaving.statistics.PageDuration *)";

    private StatisticHandler<StatisticEvent> handler = new StatisticHandler(StatisticProvider.getInstance().getStrategy());

    public StatisticReaper() {

    }

    private static volatile boolean enabled = true;

    @Pointcut(POINT_CUT_ONEVENT)
    public void withinMethodAnnotated() {
    }

    @Pointcut(POINT_CUT_ONTYPE)
    public void withinClassAnnotated() {
    }

    @Pointcut("this(android.app.Activity+) && execution(protected * *.onCreate(android.os.Bundle))")
    public void activityOnCreate() {
    }

    @Pointcut("this(android.app.Activity+) && execution(protected * *.onDestroy())")
    public void activityOnDestroy() {
    }

    @Pointcut("this(android.support.v4.app.Fragment+) && execution(protected * *.onViewCreated(android.view.View,android.os.Bundle))")
    public void fragmentOnCreateView() {
    }

    @Pointcut("this(android.support.v4.app.Fragment+) && execution(protected * *.onDestroyView())")
    public void fragmentOnDestroyView() {
    }


    @Around("withinMethodAnnotated()")
    public Object onEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        markEvent(joinPoint);
        return joinPoint.proceed();
    }

    //    @Around("withinClassAnnotated(duration) && activityOnCreate()")
    @Around("withinClassAnnotated() && activityOnCreate()")
    public Object onActivityCreated(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        markDuration(joinPoint, EventType.PAGE_START);
        return result;
    }

    //    @Around("withinClassAnnotated(duration) && activityOnDestroy()")
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

    private void markEvent(JoinPoint joinPoint) {
        if (!enabled) return;
        Object[] args = joinPoint.getArgs();
        Context context = (Context) args[0];
        StatisticEvent event = (StatisticEvent) args[1];
        handler.onEvent(context, event);
    }

    private void markDuration(JoinPoint joinPoint, EventType eventType) throws IllegalAccessException, InstantiationException {
        if (!enabled) return;
        Context context;
        Object aThis = joinPoint.getThis();
        if (aThis instanceof Activity) {
            context = (Activity) aThis;
        } else if (aThis instanceof Fragment) {
            context = ((Fragment) aThis).getContext();
        } else {
            throw new IllegalStateException("only use @PageDuration on Activity or Fragment ");
        }
        Class<?> aClass = aThis.getClass();
        PageDuration duration = aClass.getAnnotation(PageDuration.class);
        Class<?> clazz = duration.clazz();
        StatisticEvent event = (StatisticEvent) clazz.newInstance();
        event.setPageName(duration.pageName());
        event.setType(eventType);
        handler.onEvent(context, event);
    }


}
