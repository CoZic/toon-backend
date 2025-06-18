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

        /*
        // 2. 외부(application.yml)에서 주입받은 uploadPath 변수를 사용하여 리소스 경로를 동적으로 설정합니다.
            이미지 경로 요청이 /uploads/**로 들어오면, 실제 파일 시스템의 uploadPath 경로에서 해당 파일을 찾도록 설정합니다.
                예시:
                    uploadPath가 "/var/www/uploads/" 라면,
                        요청 URL이 "/uploads/[image.jpg]"일 때,
                        실제 파일 경로는 "/var/www/uploads/[image.jpg]"가 됩니다.
         */
        String resourceUrlPath = "/uploads/**";
        String resourcePath = "file:///" + uploadPath;

        registry
            .addResourceHandler(resourceUrlPath) // 리소스 URL 경로 설정
            .addResourceLocations(resourcePath); // 리소스 파일이 실제로 저장된 경로 설정
    }



}
