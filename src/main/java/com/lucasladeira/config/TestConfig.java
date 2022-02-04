package com.lucasladeira.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lucasladeira.entities.AppUser;
import com.lucasladeira.entities.Role;
import com.lucasladeira.repositories.AppUserRepository;
import com.lucasladeira.repositories.RoleRepository;
import com.lucasladeira.services.AppUserService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		
		Role r1 = new Role(null, "ADMIN");
		Role r2 = new Role(null, "USER");
		roleRepository.saveAll(Arrays.asList(r1, r2));
	
		
		AppUser u1 = new AppUser(null, "oLadeira", bCryptPasswordEncoder.encode("4321"));
		AppUser u2 = new AppUser(null, "henriquee232", bCryptPasswordEncoder.encode("44112"));
		AppUser u3 = new AppUser(null, "pedrin51", bCryptPasswordEncoder.encode("umasenhaqualquer"));
						
		appUserRepository.saveAll(Arrays.asList(u1, u2, u3));
		
		appUserService.addRoleToUser("oLadeira", "ADMIN");
	}
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
