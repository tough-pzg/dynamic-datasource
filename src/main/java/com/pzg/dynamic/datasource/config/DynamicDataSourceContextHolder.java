package com.pzg.dynamic.datasource.config;

/**
 * @author pzg
 * @description 使用 ThreadLocal 保存当前线程使用的数据源名称
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDataSource(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
