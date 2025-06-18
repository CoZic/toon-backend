package com.be.prac_toon.config; // 패키지명은 당신의 프로젝트에 맞게 조정하세요.

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // 람다식 설정을 위한 임포트

@Configuration
@EnableWebSecurity // Spring Security를 활성화합니다.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호를 비활성화합니다. 개발 단계에서 편리하지만,
                // 실제 서비스에서는 적절한 CSRF 방어 전략을 사용해야 합니다.
                .csrf(AbstractHttpConfigurer::disable)
                // 요청에 대한 접근 권한을 설정합니다.
                .authorizeHttpRequests(authorize -> authorize
                        // Spring Boot가 정적 리소스(js, css, img)를 제공하는 모든 경로를 자동으로 파악하여 허용
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                "/",
                                "/index.html",                              // 웰컴페이지 허용
                                "/api/hello", "/api/webtoons/**",           // 프론트-백 API 연결 허용
                                "/uploads/**",                              // 업로드된 이미지 접근 허용
                                "/api/register", "/api/login").permitAll()  // 필요하다면 /api/login도 추가
                        // 다른 모든 요청은 인증을 받아야 접근 가능합니다.
                        .anyRequest().authenticated()
                );
        // 폼 로그인 또는 HTTP Basic 인증 등 다른 인증 방식은 현재 명시하지 않았습니다.
        // 필요에 따라 .formLogin(), .httpBasic() 등을 추가할 수 있습니다.

        return http.build();
    }
}