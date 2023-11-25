package com.dingdong.picmap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
        	.addMapping("/**")
        	.allowedOrigins("http://localhost:8081", "http://localhost:3000")
        	.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        	.allowedHeaders("Authorization", "Content-Type")
        	.exposedHeaders("Custom-Header")
        	.maxAge(3600)
        	.allowCredentials(true);
    }
}
