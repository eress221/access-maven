package com.eress.bootmaven.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@EnableTransactionManagement
@MapperScan(value = {"com.eress.bootmaven.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@Configuration
public class DatabaseConfigAccessDB {

//    @Value("${mariadb.datasource.driverClassName:org.mariadb.jdbc.Driver}")
//    private String driverClassName;
//
//    @Value("${mariadb.datasource.url:jdbc:mariadb://localhost:3306/test}")
//    private String url;
//
//    @Value("${mariadb.datasource.username:test}")
//    private String username;
//
//    @Value("${mariadb.datasource.password:test}")
//    private String password;

//    @Bean
//    @Primary
//    public BasicDataSource dataSource() {
//        log.debug("driverClassName : " + driverClassName);
//        log.debug("url : " + url);
//        log.debug("username : " + username);
//        log.debug("password : " + password);
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setValidationQuery("/* ping */ SELECT 1");
//        dataSource.setTestOnBorrow(true);
//        return dataSource;
//    }

    @Bean
    @Primary
    @ConfigurationProperties("accessdb.datasource")
    public DataSourceProperties dataSourceProperties() {
//        try {
//            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public BasicDataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().type(BasicDataSource.class).build();
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.eress.bootmaven.model");
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(
                        "classpath*:mapper/*.xml"
                )
        );
//        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSession1(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }
}
