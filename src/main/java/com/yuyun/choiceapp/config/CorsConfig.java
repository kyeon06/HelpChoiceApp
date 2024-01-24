package com.yuyun.choiceapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://develop-me-roan.vercel.app")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
}