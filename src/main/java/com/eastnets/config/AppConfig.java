package com.eastnets.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.eastnets")
@PropertySource("classpath:application.properties")
public class AppConfig {



    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String username;

    @Value("${db.password}")
    private String password;


    @Bean
    public DataSource myDataSource() {
        System.out.println(url + " " + username);
         DriverManagerDataSource dataSource = new DriverManagerDataSource(url , username , password);
         dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
         return dataSource;

    }

}
