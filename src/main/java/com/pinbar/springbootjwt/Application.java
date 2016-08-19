package com.pinbar.springbootjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    JWTAuthFilter jwtAuthFilter;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public FilterRegistrationBean chaosFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ChaosFilter());
        registrationBean.addUrlPatterns("/oracle/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
    
    @Bean
    public FilterRegistrationBean jwtAuthFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(jwtAuthFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        
        return registrationBean;
    }
    
}
