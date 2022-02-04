package com.lucasladeira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AutenticacaoAutorizacaoOAuth2JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutenticacaoAutorizacaoOAuth2JwtApplication.class, args);
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
}
