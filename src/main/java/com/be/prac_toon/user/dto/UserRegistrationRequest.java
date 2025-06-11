package com.be.prac_toon.user.dto; // DTO를 위한 새로운 패키지

public class UserRegistrationRequest {
    private String username; // Vue에서 'id'로 보낸 값이 백엔드에서는 'username'
    private String email;
    private String password;

    // 기본 생성자
    public UserRegistrationRequest() {
    }

    // 모든 필드를 포함하는 생성자
    public UserRegistrationRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getter 및 Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                // 보안상 비밀번호는 toString()에 포함하지 않는 것이 좋습니다.
                '}';
    }
}