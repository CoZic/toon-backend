package com.be.prac_toon.user.controller;

import com.be.prac_toon.user.service.UserService;
import com.be.prac_toon.user.domain.User;
import com.be.prac_toon.user.dto.UserRegistrationRequest; // 새로 만든 DTO 임포트
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // 이 컨트롤러의 모든 엔드포인트는 "/api"로 시작합니다.
public class UserController {

    private final UserService userService; // UserService는 User 엔티티를 다룸

    public UserController(UserService userService) {
        this.userService = userService;
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


    // 기존 User 생성 (예: 관리자용 또는 다른 목적)
    // 이 엔드포인트는 Vue의 회원가입 페이지에서 사용되지 않습니다.
    // 필요하다면 @RequestMapping을 변경하거나 별도의 DTO를 사용하는 것이 좋습니다.
    @PostMapping("/users") // 기존 "/api/users" POST
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // 이 메서드는 직접 User 엔티티를 받는 경우에만 사용합니다.
        // 예를 들어 관리자가 직접 사용자를 추가하는 경우 등.
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // 201 Created 응답
    }

    // GET 요청을 처리하여 모든 User 목록을 조회합니다.
    @GetMapping("/users") // 기존 "/api/users" GET
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK); // 200 OK 응답
    }
}