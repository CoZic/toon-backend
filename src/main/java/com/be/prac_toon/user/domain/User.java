package com.be.prac_toon.user.domain;

import com.be.prac_toon.content.domain.Content;
import com.be.prac_toon.content.domain.EpisodeLike;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
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

*/

// =====================================================================================================================

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Table(name = "user") // 데이터베이스 테이블 이름을 "user"로 지정합니다. (일반적으로 소문자 복수형 사용)
@Getter                     // 모든 필드에 대한 Getter 메소드를 자동으로 생성합니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터 없는 기본 생성자를 보호된 접근 수준으로 생성합니다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING) // Enum의 이름을 DB에 문자열로 저장
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider; // 'LOCAL', 'GOOGLE', 'KAKAO'

    private String providerId; // 소셜 로그인 공급자의 고유 ID

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Instant lastLoginAt;

    // User(1)가 Content(N)를 등록하는 관계
    @OneToMany(mappedBy = "registrant")
    private List<Content> registeredContents = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<EpisodeLike> likes = new HashSet<>();

    @Builder
    public User(String email, String password, String nickname, String profileImageUrl, Provider provider, String providerId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.provider = provider;
        this.providerId = providerId;
        this.status = UserStatus.ACTIVE; // 생성 시 기본 상태는 '활성'
    }

}