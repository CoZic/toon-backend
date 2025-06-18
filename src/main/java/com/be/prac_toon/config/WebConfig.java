package com.be.prac_toon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. @Value 어노테이션을 사용해 application.yml의 값을 주입받습니다.
    @Value("${custom.upload-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 2. 외부(application.yml)에서 주입받은 uploadPath 변수를 사용하여 리소스 경로를 동적으로 설정합니다.
        String resourcePath = "file:///" + uploadPath;
        String resourceUrlPath = "/uploads/**";

        registry.addResourceHandler(uploadPath)
                .addResourceLocations(resourcePath);
    }



}
