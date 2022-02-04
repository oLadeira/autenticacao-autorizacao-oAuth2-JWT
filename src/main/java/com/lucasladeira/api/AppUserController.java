package com.lucasladeira.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasladeira.entities.AppUser;
import com.lucasladeira.entities.Role;
import com.lucasladeira.entities.RoleToUser;
import com.lucasladeira.services.AppUserService;

@RestControllerAdvice
@RequestMapping("/api")
public class AppUserController {

	@Autowired
	private AppUserService appUserService;
	
	
	@PostMapping("/user/save")
	public ResponseEntity<Void> saveUser(@RequestBody AppUser user){
		appUserService.saveUser(user);
		URI uri = URI
				.create(ServletUriComponentsBuilder
							.fromCurrentContextPath()
							.path("/api/user/save")
						.toUriString());
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("role/save")
	public ResponseEntity<Void> saveRole(@RequestBody Role role){
		appUserService.saveRole(role);
		URI uri = URI
				.create(ServletUriComponentsBuilder
							.fromCurrentContextPath()
							.path("/api/role/save")
						.toUriString());
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("role/addtouser")
	public ResponseEntity<Void> addRoleToUser(@RequestBody RoleToUser roleToUser){
		appUserService.addRoleToUser(roleToUser.getUsername(), roleToUser.getRoleName());
		URI uri = URI
				.create(ServletUriComponentsBuilder
							.fromCurrentContextPath()
							.path("/api/role/addtouser")
						.toUriString());
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers(){
		return ResponseEntity.ok().body(appUserService.getUsers());
	}
	
	@GetMapping("user/{username}")
	public ResponseEntity<AppUser> getUser(@PathVariable String username){
		return ResponseEntity.ok().body(appUserService.getUser(username));
	}
	
	
}
