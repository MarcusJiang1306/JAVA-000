package com.marcus.sbmybatis.datasource.aop;

import com.marcus.sbmybatis.datasource.DynamicDataSource;
import com.marcus.sbmybatis.util.DatasourceConst;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect implements Ordered {
    @Pointcut("@annotation(com.marcus.sbmybatis.datasource.CurDataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DynamicDataSource.setDataSource(DatasourceConst.secondary.name());

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
