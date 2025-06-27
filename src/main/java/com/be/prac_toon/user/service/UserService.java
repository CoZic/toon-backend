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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 신규 회원가입 처리 메서드
    @Transactional
    public User registerNewUser(String username, String email, String plainPassword) {
        // 1. 아이디 또는 이메일 중복 검사 (예시)
//        if (userRepository.findByUsername(username).isPresent()) {
//            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
//        }
        if (userRepository.findByNickname(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 2. User 엔티티 생성 및 비밀번호 암호화
    /*
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(plainPassword)); // 중요: 비밀번호 암호화
    */
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
        // 이 메서드는 DTO 없이 User 엔티티를 직접 저장할 때 사용합니다.
        // 비밀번호가 이미 암호화되어있다고 가정하거나, 이전에 암호화 로직을 수행해야 합니다.
        // registerNewUser와 역할이 겹칠 수 있으니 적절히 사용하거나 통합하세요.
        return userRepository.save(user); // User 정보를 데이터베이스에 저장합니다.
    }

    // 모든 사용자 조회 (예시)
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }


    public String loginAndGetToken(String email, String password) {
        // Optional에서 User 객체 추출 (없으면 예외 던짐)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일이 존재하지 않습니다."));

        // 비밀번호 일치 여부 확인
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 발급
        return JwtUtil.generateToken(user.getEmail());
    }
}