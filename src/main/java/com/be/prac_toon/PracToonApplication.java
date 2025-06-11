package com.be.prac_toon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PracToonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracToonApplication.class, args);
	}
	// PasswordEncoder 빈 등록 (비밀번호 암호화를 위해 필수)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 강력한 암호화 방식인 BCrypt 사용
	}

}