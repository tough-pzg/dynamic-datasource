package com.pzg.dynamic.datasource.config;

import com.pzg.dynamic.datasource.aspect.DataSourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pzg
 * @description 动态数据源配置：实例化 DataSourceRegister 和 DynamicDataSource
 */
@Configuration
public class BeanConfig {

    @Bean
    public DataSourceRegister dynamicDataSourceRegister(){
        return new DataSourceRegister();
    }

    @Bean
    public DynamicDataSource dynamicDataSource(DataSourceRegister dataSourceRegister){
        return new DynamicDataSource(dataSourceRegister.getDefaultDataSource(), dataSourceRegister.getCustomDataSources());
    }

    @Bean
    public DataSourceAspect dataSourceAspect(){
        return new DataSourceAspect();
    }

}
