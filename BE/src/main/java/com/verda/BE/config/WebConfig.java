package com.verda.BE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${front.localhost}")
    private String frontLocal;

    @Value("${front.vercel}")
    private String frontVercel;

    @Value("${back.aws}")
    private String backServerApi;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        frontLocal,
                        frontVercel,
                        backServerApi
                )
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")

        ;
    }
}
