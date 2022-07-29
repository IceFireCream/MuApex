package com.mu.web.api.impl.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
public class MuDruidConfiguration {
    @Value("spring.datasource.url")
    private String url;
    @Value("spring.datasource.username")
    private String username;
    @Value("spring.datasource.password")
    private String password;
    @Value("spring.datasource.driver-class-name")
    private String diverClassName;

    @Bean
    @RefreshScope
    public DruidDataSource dataSource(){

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(diverClassName);

        return dataSource;
    }

}
