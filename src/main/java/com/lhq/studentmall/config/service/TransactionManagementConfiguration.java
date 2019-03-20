package com.lhq.studentmall.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;


/**
 * @author Leon
 * @ClassName: TransactionManagementConfiguration
 * @Description: 继承TransactionManagementConfigurer是为了开启事务,在service方法上添加注解@Transactional便可
 * @date 2019/3/20 20:20
 */
@Configuration
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {
    //注入datasourceconfiguration里边的datasource
    @Autowired
    private DataSource dataSource;

    /**
     * 功能描述:开启事务支持需要返回这个来实现
     *
     * @param:
     * @return:
     **/
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
