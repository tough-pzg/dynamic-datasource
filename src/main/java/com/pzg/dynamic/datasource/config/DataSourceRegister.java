package com.pzg.dynamic.datasource.config;

import com.pzg.dynamic.datasource.bean.HikariCpConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author pzg
 * @description 动态数据源注册器
 */
public class DataSourceRegister implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DataSourceRegister.class);

    //默认数据源（主数据源）
    private DataSource defaultDataSource;
    //自定义数据源
    private Map<Object, Object> customDataSources = new HashMap<>();

    //HikariCpConfig 配置合并器
    private static final ConfigMergeCreator<HikariCpConfig, HikariConfig> MERGE_CREATOR = new ConfigMergeCreator<>("HikariCp", HikariCpConfig.class, HikariConfig.class);

    //默认的Hikari连接池全局配置
    private static final HikariCpConfig GLOBAL_HIKARI_CP_CONFIG = new HikariCpConfig();

    static {
        GLOBAL_HIKARI_CP_CONFIG.setMaximumPoolSize(10);
        GLOBAL_HIKARI_CP_CONFIG.setIdleTimeout(600000L);
        GLOBAL_HIKARI_CP_CONFIG.setAutoCommit(true);
        GLOBAL_HIKARI_CP_CONFIG.setMaxLifetime(1800000L);
        GLOBAL_HIKARI_CP_CONFIG.setConnectionTimeout(30000L);
    }

    /**
     * 凡注册到Spring容器内的bean，实现了EnvironmentAware接口重写setEnvironment方法后，在工程启动时可以获得application.properties的配置文件配置的属性值
     * @param environment 环境
     */
    @Override
    public void setEnvironment(Environment environment) {
        initDefaultDataSource(environment);
        initCustomDataSources(environment);
    }

    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    public Map<Object, Object> getCustomDataSources() {
        return customDataSources;
    }

    /**
     * 初始化默认数据源
     * @param env
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源配置
        Map<String, Object> dataSourceMap = new HashMap<>();
        //目前版本限制使用 Hikari
//        dataSourceMap.put("type", env.getProperty("spring.datasource.type"));
        dataSourceMap.put("driver-class-name", env.getProperty("spring.datasource.driver-class-name"));
        dataSourceMap.put("url", env.getProperty("spring.datasource.url"));
        dataSourceMap.put("username", env.getProperty("spring.datasource.username"));
        dataSourceMap.put("password", env.getProperty("spring.datasource.password"));

        //hikari 连接池 配置信息
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
        Binder binder = new Binder(sources);
        BindResult<HikariCpConfig> bindResult = binder.bind("spring.datasource.hikari", HikariCpConfig.class);
        HikariCpConfig hikariCpConfig;
        try {
            hikariCpConfig = bindResult.get();
        } catch (Exception e) {
            hikariCpConfig = new HikariCpConfig();
        }
        defaultDataSource = buildDataSource("default",dataSourceMap,hikariCpConfig);
    }

    /**
     * 初始化自定义数据源
     * @param env
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        String dataSourceNames = env.getProperty("spring.datasource.names");
        if (StringUtils.isNotBlank(dataSourceNames)) {
            for (String dataSourceName : dataSourceNames.split(",")) {// 多个数据源
                Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
                Binder binder = new Binder(sources);
                BindResult<Properties> bindResult = binder.bind("spring.datasource." + dataSourceName, Properties.class);
                Properties properties = bindResult.get();
                Map<String, Object> dataSourceMap = new HashMap<>();
                dataSourceMap.put("type", properties.getProperty("type"));
                dataSourceMap.put("driver-class-name", properties.getProperty("driverClassName"));
                dataSourceMap.put("url", properties.getProperty("url"));
                dataSourceMap.put("username", properties.getProperty("username"));
                dataSourceMap.put("password", properties.getProperty("password"));

                //hikari 连接池 配置信息
                BindResult<HikariCpConfig> hikariResult = binder.bind("spring.datasource." + dataSourceName + ".hikari", HikariCpConfig.class);
                HikariCpConfig hikariCpConfig;
                try {
                    hikariCpConfig = hikariResult.get();
                } catch (Exception e) {
                    hikariCpConfig = new HikariCpConfig();
                }
                customDataSources.put(dataSourceName,buildDataSource(dataSourceName,dataSourceMap,hikariCpConfig));
            }
        }
    }

    /**
     * 创建数据源
     * @param dsMap
     * @return
     */
    private DataSource buildDataSource(String dataSourceName,Map<String, Object> dsMap, HikariCpConfig hikariCpConfig) {
        try {
            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            //限制，只使用 Hikari 数据源连接池
            HikariDataSource dataSource = new HikariDataSource();
            //合并连接池配置
            HikariConfig hikariConfig = MERGE_CREATOR.create(GLOBAL_HIKARI_CP_CONFIG, hikariCpConfig);
            //设置数据源属性
            hikariConfig.setDriverClassName(driverClassName);
            hikariConfig.setJdbcUrl(url);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);

            //连接池属性 复制到 数据源实例上
            hikariConfig.copyStateTo(dataSource);
            log.info("创建[{}]数据源,url:{}",dataSourceName,url);
            return dataSource;
        } catch (Exception e) {
            log.error("使用配置文件，创建数据源时出现异常", e);
        }
        return null;
    }

}
