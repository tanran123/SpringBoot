
package com.ginko.driver.api.config.datasourceConfig;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


/**
 * @Author: tran
 * @Description:数据源配置
 * @Date Create in 16:43 2019/6/17
 */

@Configuration
public class DataSourceConfig {
    Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);


    /**
     * 数据源配置 - 使用C3P0
     * @param driverClass
     * @param jdbcUrl
     * @param user
     * @param pwd
     * @param minPoolSize
     * @param maxPoolSize
     * @param initPoolSize
     * @param acIncrement
     * @param acRetryAttempts
     * @param maxIdleTime
     * @param maxStatement
     * @param idleConn
     * @param breakAfterAcFailure
     * @param testConn
     * @return
     */
    @Bean(name = "mysqlDataSource")
    @Qualifier("mysqlDataSource")
    @Primary
    public ComboPooledDataSource createDataPoll(@Value("${spring.mysql.datasource.driver-class-name}") String driverClass,
                                                @Value("${spring.mysql.datasource.url}") String jdbcUrl,
                                                @Value("${spring.mysql.datasource.username}") String user,
                                                @Value("${spring.mysql.datasource.password}") String pwd,
                                                @Value("${spring.mysql.datasource.minPoolSize}") int minPoolSize,
                                                @Value("${spring.mysql.datasource.maxPoolSize}") int maxPoolSize,
                                                @Value("${spring.mysql.datasource.initialPoolSize}") int initPoolSize,
                                                @Value("${spring.mysql.datasource.acquireIncrement}") int acIncrement,
                                                @Value("${spring.mysql.datasource.acquireRetryAttempts}") int acRetryAttempts,
                                                @Value("${spring.mysql.datasource.maxIdleTime}") int maxIdleTime,
                                                @Value("${spring.mysql.datasource.maxStatements}") int maxStatement,
                                                @Value("${spring.mysql.datasource.idleConnectionTestPeriod}") int idleConn,
                                                @Value("${spring.mysql.datasource.breakAfterAcquireFailure}") boolean breakAfterAcFailure,
                                                @Value("${spring.mysql.datasource.testConnectionOnCheckout}") boolean testConn) {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(driverClass);
        } catch (PropertyVetoException e) {
            logger.error("driverClass 异常请检查驱动类型", e);
        }
        comboPooledDataSource.setJdbcUrl(jdbcUrl);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(pwd);
        comboPooledDataSource.setMinPoolSize(minPoolSize);
        comboPooledDataSource.setMaxPoolSize(maxPoolSize);
        comboPooledDataSource.setInitialPoolSize(initPoolSize);
        comboPooledDataSource.setAcquireIncrement(acIncrement);
        comboPooledDataSource.setAcquireRetryAttempts(acRetryAttempts);
        comboPooledDataSource.setMaxIdleTime(maxIdleTime);
        comboPooledDataSource.setMaxStatements(maxStatement);
        comboPooledDataSource.setIdleConnectionTestPeriod(idleConn);
        comboPooledDataSource.setBreakAfterAcquireFailure(breakAfterAcFailure);
        comboPooledDataSource.setTestConnectionOnCheckout(testConn);
        return comboPooledDataSource;
    }
}

