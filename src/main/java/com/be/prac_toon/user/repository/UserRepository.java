package com.be.prac_toon.user.repository;

import com.be.prac_toon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Optional 임포트

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임 중복 검사용
    Optional<User> findByNickname(String nickname);

    // 이메일로 로그인 및 중복 검사
    Optional<User> findByEmail(String email);
}