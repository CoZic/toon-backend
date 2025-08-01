package com.be.prac_toon.user.controller;

import com.be.prac_toon.user.dto.UserLoginRequest;
import com.be.prac_toon.user.service.UserService;
import com.be.prac_toon.user.domain.User;
import com.be.prac_toon.user.dto.UserRegistrationRequest; // 새로 만든 DTO 임포트
import com.be.prac_toon.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api") // 이 컨트롤러의 모든 엔드포인트는 "/api"로 시작합니다.
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService; // UserService는 User 엔티티를 다룸

    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입 엔드포인트 (Vue에서 "/api/register"로 요청 보낼 예정)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        // 1. 입력 유효성 검사 (예: 필드 누락 여부, 비밀번호 길이 등)
        if (registrationRequest.getUsername() == null || registrationRequest.getUsername().isEmpty() ||
                registrationRequest.getEmail() == null || registrationRequest.getEmail().isEmpty() ||
                registrationRequest.getPassword() == null || registrationRequest.getPassword().isEmpty()) {
            return new ResponseEntity<>("모든 필수 정보를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }

        // 2. UserService를 통해 회원가입 로직 처리
        // DTO에서 엔티티로 변환하고, 비밀번호 암호화 등 비즈니스 로직 수행
        try {
            userService.registerNewUser(
                    registrationRequest.getUsername(),
                    registrationRequest.getEmail(),
                    registrationRequest.getPassword()
            );
            return new ResponseEntity<>("회원가입이 성공적으로 완료되었습니다.", HttpStatus.CREATED);
        } catch (Exception e) { // 실제로는 더 구체적인 예외 처리 필요 (예: 아이디 중복, 이메일 중복)
            // 에러 로깅
            System.err.println("회원가입 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {

        log.info("로그인 컨트롤러 진입 - email: {}", loginRequest.getEmail());

        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return new ResponseEntity<>("이메일과 비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }

        try {
            String token = userService.loginAndGetToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            return ResponseEntity.ok().body(Map.of("token", token)); // {"token": "..."}

        } catch (Exception e) {
            return new ResponseEntity<>("로그인 실패: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 Authorization 헤더입니다.");
            }

            String token = authHeader.substring(7); // "Bearer " 제거
            String email = jwtUtil.getSubject(token); // 토큰에서 이메일 추출

            User user = userService.findByEmail(email); // 사용자 조회 (직접 만든 서비스 메서드)

            log.info("내 정보 확인 용!  - : {}", user.getId() + user.getEmail());

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", user.getEmail());
            userInfo.put("username", user.getNickname());
            userInfo.put("providerId", user.getProviderId());
            userInfo.put("profileImageUrl", user.getProfileImageUrl());
            userInfo.put("status", user.getStatus());

            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }


}