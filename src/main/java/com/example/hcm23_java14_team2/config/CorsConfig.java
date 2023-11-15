package com.example.hcm23_java14_team2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5000") // Thay bằng URL của ứng dụng React
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true);    
    }
}
