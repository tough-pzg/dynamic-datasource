package com.pzg.dynamic.datasource.config;

import com.pzg.dynamic.datasource.aspect.DynamicDataSourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pzg
 * @description 动态数据源配置：实例化 DynamicDataSourceRegister 和 DynamicDataSource
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    public DynamicDataSourceRegister dynamicDataSourceRegister(){
        return new DynamicDataSourceRegister();
    }

    @Bean
    public DynamicDataSource dynamicDataSource(DynamicDataSourceRegister dynamicDataSourceRegister){
        return new DynamicDataSource(dynamicDataSourceRegister.getDefaultDataSource(),dynamicDataSourceRegister.getCustomDataSources());
    }

    @Bean
    public DynamicDataSourceAspect dataSourceAspect(){
        return new DynamicDataSourceAspect();
    }

}
