package com.pzg.dynamic.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * @author pzg
 * @description  动态数据源 ： determineCurrentLookupKey() 决定当前使用的数据源是哪一个
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(Object defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(defaultTargetDataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
       return DataSourceContextHolder.getDataSource();
    }


}