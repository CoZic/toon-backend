package com.be.prac_toon.user;

import jakarta.persistence.Entity; // JPA 엔티티를 위한 임포트
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // JPA 기본 키를 위한 임포트 (올바른 경로)
import jakarta.persistence.Table; // 테이블 이름을 지정하기 위한 임포트

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Table(name = "users") // 데이터베이스 테이블 이름을 "users"로 지정합니다. (일반적으로 소문자 복수형 사용)
public class User {

    @Id // 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임합니다 (MySQL의 AUTO_INCREMENT).
    private Long id; // 사용자 고유 ID

    private String username; // 사용자 이름 (또는 아이디)
    private String email;    // 이메일
    private String password; // 비밀번호

    // 기본 생성자 (JPA 사용 시 필수)
    public User() {
    }

    // 모든 필드를 포함하는 생성자 (편의상)
    // 일반적으로 이 생성자는 회원가입 시 사용되지 않습니다. (id는 DB가 생성, password는 암호화 필요)
    // 테스트 목적이나 특정 상황에서만 사용을 고려하세요.
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // "username" 필드에 대한 Getter
    public String getUsername() {
        return username;
    }

    // "username" 필드에 대한 Setter
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // "password" 필드에 대한 Getter
    public String getPassword() {
        return password;
    }

    // "password" 필드에 대한 Setter
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                // 보안상 비밀번호는 toString()에 포함하지 않는 것이 좋습니다.
                '}';
    }
}