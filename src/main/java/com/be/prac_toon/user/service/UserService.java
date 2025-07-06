package com.be.prac_toon.user.service;

import com.be.prac_toon.user.domain.Provider;
import com.be.prac_toon.user.domain.User;
import com.be.prac_toon.user.repository.UserRepository;
import com.be.prac_toon.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder; // 비밀번호 암호화를 위해 추가 (아래 설명 참조)

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 빈 주입
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 신규 회원가입 처리 메서드
    @Transactional
    public User registerNewUser(String username, String email, String plainPassword) {
        // 1. 아이디 또는 이메일 중복 검사 (예시)
        if (userRepository.findByNickname(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 2. User 엔티티 생성 및 비밀번호 암호화
        User newUser = User.builder()
                .nickname(username)
                .email(email)
                .password(passwordEncoder.encode(plainPassword))
                .provider(Provider.LOCAL) // 회원가입 방식 : 일반
                .build();

        // 3. 데이터베이스에 저장
        return userRepository.save(newUser);
    }


    @Transactional // 메서드 실행 중 트랜잭션을 관리합니다.
    public User saveUser(User user) {

        return userRepository.save(user); // User 정보를 데이터베이스에 저장합니다.
    }

    // 모든 사용자 조회 (예시)
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }


    public String loginAndGetToken(String email, String plainPassword) {
        // Optional에서 User 객체 추출 (없으면 예외 던짐)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일이 존재하지 않습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(plainPassword, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 발급
        return jwtUtil.generateToken(user.getEmail());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}