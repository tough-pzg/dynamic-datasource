/*
 * Copyright © 2018 organization baomidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pzg.dynamic.datasource.bean;


import java.util.Properties;

/**
 * HikariCp参数配置
 * @author pzg
 */
public class HikariCpConfig {

    private String poolName;
    private String catalog;
    private Long connectionTimeout;
    private Long validationTimeout;
    private Long idleTimeout;
    private Long leakDetectionThreshold;
    private Long maxLifetime;
    private Integer maxPoolSize;
    private Integer maximumPoolSize;
    private Integer minIdle;
    private Integer minimumIdle;

    private Long initializationFailTimeout;
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String dataSourceJndiName;
    private String transactionIsolationName;
    private Boolean isAutoCommit;
    private Boolean isReadOnly;
    private Boolean isIsolateInternalQueries;
    private Boolean isRegisterMbeans;
    private Boolean isAllowPoolSuspension;
    private Properties dataSourceProperties;
    private Properties healthCheckProperties;

    /**
     * 高版本才有
     */
    private String schema;
    private String exceptionOverrideClassName;
    private Long keepaliveTime;
    private Boolean sealed;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Long getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(Long validationTimeout) {
        this.validationTimeout = validationTimeout;
    }

    public Long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(Long leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }

    public Long getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(Integer minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public Long getInitializationFailTimeout() {
        return initializationFailTimeout;
    }

    public void setInitializationFailTimeout(Long initializationFailTimeout) {
        this.initializationFailTimeout = initializationFailTimeout;
    }

    public String getConnectionInitSql() {
        return connectionInitSql;
    }

    public void setConnectionInitSql(String connectionInitSql) {
        this.connectionInitSql = connectionInitSql;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    public void setDataSourceClassName(String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }

    public String getDataSourceJndiName() {
        return dataSourceJndiName;
    }

    public void setDataSourceJndiName(String dataSourceJndiName) {
        this.dataSourceJndiName = dataSourceJndiName;
    }

    public String getTransactionIsolationName() {
        return transactionIsolationName;
    }

    public void setTransactionIsolationName(String transactionIsolationName) {
        this.transactionIsolationName = transactionIsolationName;
    }

    public Boolean getAutoCommit() {
        return isAutoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        isAutoCommit = autoCommit;
    }

    public Boolean getReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        isReadOnly = readOnly;
    }

    public Boolean getIsolateInternalQueries() {
        return isIsolateInternalQueries;
    }

    public void setIsolateInternalQueries(Boolean isolateInternalQueries) {
        isIsolateInternalQueries = isolateInternalQueries;
    }

    public Boolean getRegisterMbeans() {
        return isRegisterMbeans;
    }

    public void setRegisterMbeans(Boolean registerMbeans) {
        isRegisterMbeans = registerMbeans;
    }

    public Boolean getAllowPoolSuspension() {
        return isAllowPoolSuspension;
    }

    public void setAllowPoolSuspension(Boolean allowPoolSuspension) {
        isAllowPoolSuspension = allowPoolSuspension;
    }

    public Properties getDataSourceProperties() {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Properties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public Properties getHealthCheckProperties() {
        return healthCheckProperties;
    }

    public void setHealthCheckProperties(Properties healthCheckProperties) {
        this.healthCheckProperties = healthCheckProperties;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getExceptionOverrideClassName() {
        return exceptionOverrideClassName;
    }

    public void setExceptionOverrideClassName(String exceptionOverrideClassName) {
        this.exceptionOverrideClassName = exceptionOverrideClassName;
    }

    public Long getKeepaliveTime() {
        return keepaliveTime;
    }

    public void setKeepaliveTime(Long keepaliveTime) {
        this.keepaliveTime = keepaliveTime;
    }

    public Boolean getSealed() {
        return sealed;
    }

    public void setSealed(Boolean sealed) {
        this.sealed = sealed;
    }
}
