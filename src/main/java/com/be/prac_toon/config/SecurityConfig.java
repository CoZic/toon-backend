package com.be.prac_toon.config; // 패키지명은 당신의 프로젝트에 맞게 조정하세요.

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // 람다식 설정을 위한 임포트
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Spring Security를 활성화합니다.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호를 비활성화합니다. 개발 단계에서 편리하지만,
                // 실제 서비스에서는 적절한 CSRF 방어 전략을 사용해야 합니다.
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults()) // <-- 이거 꼭 추가
                // 요청에 대한 접근 권한을 설정합니다.
                .authorizeHttpRequests(authorize -> authorize
                        // [추가] 좋아요(POST) API는 인증을 요구하도록 설정
                        // 이 규칙을 permitAll() 규칙보다 먼저 정의해야 합니다.
//                        .requestMatchers(HttpMethod.POST, "/api/episodes/*/like").authenticated()
                        // Spring Boot가 정적 리소스(js, css, img)를 제공하는 모든 경로를 자동으로 파악하여 허용
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/",
                                "/index.html",                              // 웰컴페이지 허용
                                "/api/**",
                                "/uploads/**",                              // 업로드된 이미지 접근 허용
                                "/api/register", "/api/login").permitAll()  // 필요하다면 /api/login도 추가
                        // 다른 모든 요청은 인증을 받아야 접근 가능합니다.
                        .anyRequest().authenticated()
                );
        // 폼 로그인 또는 HTTP Basic 인증 등 다른 인증 방식은 현재 명시하지 않았습니다.
        // 필요에 따라 .formLogin(), .httpBasic() 등을 추가할 수 있습니다.

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // 프론트 주소
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // withCredentials: true 쓸 경우 필요

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}