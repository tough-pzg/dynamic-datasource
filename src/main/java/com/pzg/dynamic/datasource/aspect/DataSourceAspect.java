package com.pzg.dynamic.datasource.aspect;

import com.pzg.dynamic.datasource.annotation.DS;
import com.pzg.dynamic.datasource.config.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @author pzg
 * @description 拦截 被UseDataSource注释的类或方法，并且依据就近原则生效
 */
@Order(1)
@Aspect
public class DataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);


    @Pointcut("@within(com.pzg.dynamic.datasource.annotation.DS)")
    public void classPointCut() {

    }

    @Pointcut("@annotation(com.pzg.dynamic.datasource.annotation.DS)")
    public void methodPointCut() {
    }


    @Around("classPointCut()")
    public Object classAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.isAnnotationPresent(DS.class) && !method.isAnnotationPresent(DS.class)) {
            DS DS = declaringClass.getAnnotation(DS.class);
            DataSourceContextHolder.setDataSource(DS.value());
            log.info("当前类设置数据源：" + DS.value());
        }

        return proceed(point);
    }

    @Around("methodPointCut()")
    public Object methodAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(DS.class)) {
            DS DS = method.getAnnotation(DS.class);
            DataSourceContextHolder.setDataSource(DS.value());
            log.info("当前方法设置数据源：" + DS.value());
        }

        return proceed(point);
    }

    private Object proceed(ProceedingJoinPoint point) throws Throwable{
        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clearDataSource();
            log.debug("清理数据源!");
        }
    }

}
