package com.vinspier.demo.cofig;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(JdbcProperties.class)
public class JdbcConfiguration1 {

    /**
     * 经典注入方式
     * */
   /* @Autowired
    private JdbcProperties jdbcProperties;*/

    /**
     * 构造参数注入
     * */
//    private JdbcProperties jdbcProperties;
//    public JdbcConfiguration(JdbcProperties jdbcProperties){
//        this.jdbcProperties = jdbcProperties;
//    }

   /* @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcProperties.getDriverClassName());
        dataSource.setUrl(jdbcProperties.getUrl());
        dataSource.setUsername(jdbcProperties.getUsername());
        dataSource.setPassword(jdbcProperties.getPassword());
        return dataSource;
    }*/

    /**
     * 形参注入方式
     * */
//    @Bean
//    public DataSource dataSource(JdbcProperties jdbcProperties){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(jdbcProperties.getDriverClassName());
//        dataSource.setUrl(jdbcProperties.getUrl());
//        dataSource.setUsername(jdbcProperties.getUsername());
//        dataSource.setPassword(jdbcProperties.getPassword());
//        return dataSource;
//    }

    /**
     * 注解式注入
     * */
    @Bean()
    @ConfigurationProperties(prefix = "jdbc")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }

}
