package com.lhq.studentmall.config.dao;


import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 * @author Leon
 * @ClassName: SessionFactoryConfiguration
 * @Description: Dao层的配置文件  配置SqlSessionFactory对象
 * @date 2019/3/20 16:54
 */
@Configuration
public class SessionFactoryConfiguration {

    @Autowired
    private DataSource dataSource;


    private static String mybatisConfigFile;
    @Value("${mybatis_config_file}")
    public void setMybatisConfigFile(String mybatisConfigFile) {
        SessionFactoryConfiguration.mybatisConfigFile = mybatisConfigFile;
    }


    private static String mapperPahth;
    @Value("${mybatis_mapper-locations}")
    public void setMapperPahth(String mapperPahth) {
        SessionFactoryConfiguration.mapperPahth = mapperPahth;
    }



    @Value("${mybatis_type-aliases-package}")
    private String typeAliasesPackage;


    /**
     * 功能描述:创建sqlSessionFactoryBean实例，并且设置configtion，设置mapper映射路径
     *         创建datasource数据源
     *
     * @param:
     * @return:
     **/
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws PropertyVetoException, IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置mybatis configuration扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFile));
        //添加mapper扫描路径
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPahth;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        //设置dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        //设置typeAlias包扫描路径
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        return sqlSessionFactoryBean;
    }

}
