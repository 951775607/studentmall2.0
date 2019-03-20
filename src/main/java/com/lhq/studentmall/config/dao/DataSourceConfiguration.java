package com.lhq.studentmall.config.dao;

import com.lhq.studentmall.util.DESUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * @author Leon
 * @ClassName: DataSourceConfiguration
 * @Description: Dao层的配置文件  配置连接池的信息
 * @date 2019/3/20 16:54
 */
@Configuration
//配置mapper dao的扫描路径
@MapperScan("com.lhq.studentmall.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driverClass}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    /**
     * 功能描述:生成spring-dao.xml对应的bean dataSource
     *
     * @param:
     * @return:
     **/
    @Bean(name="dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        //生成datasource实例
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //配置信息，与原来的spring-dao.xml方式一致
        //驱动
        dataSource.setDriverClass(jdbcDriver);
        //url
        dataSource.setJdbcUrl(jdbcUrl);
        //用户名
        dataSource.setUser(DESUtils.getDecryptString(jdbcUsername));
//        dataSource.setUser(jdbcUsername);
        //密码
//        dataSource.setPassword(jdbcPassword);
        dataSource.setPassword(DESUtils.getDecryptString(jdbcPassword));

        //c3p0连接池
        //最大连接线程数
        dataSource.setMaxPoolSize(30);
        //最小连接线程数
        dataSource.setMinPoolSize(20);
        //关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
        //连接超时时间
        dataSource.setCheckoutTimeout(10000);
        //连接失败重连次数
        dataSource.setAcquireRetryAttempts(2);

        return dataSource;
    }

}
