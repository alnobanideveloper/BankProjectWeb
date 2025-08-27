package com.eastnets.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.eastnets")
public class AppConfig {

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
    @Bean
    public EntityManager em(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        return em;
    }

    @Bean
    public EntityManagerFactory emf(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EastNetsBankPU");
        return emf;
    }
//
//    @Value("${db.url}")
//    private String url;
//
//    @Value("${db.user}")
//    private String username;
//
//    @Value("${db.password}")
//    private String password;
//
//
//    @Bean
//    public DataSource myDataSource() {
//        System.out.println(url + " " + username);
//         DriverManagerDataSource dataSource = new DriverManagerDataSource(url , username , password);
//         dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//         return dataSource;
//
//    }

}
