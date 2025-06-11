package com.be.prac_toon.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Optional 임포트

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름으로 User를 찾는 메서드 (중복 검사에 사용)
    Optional<User> findByUsername(String username);

    // 이메일로 User를 찾는 메서드 (중복 검사에 사용)
    Optional<User> findByEmail(String email);
}