package com.lucasladeira.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lucasladeira.entities.AppUser;
import com.lucasladeira.entities.Role;
import com.lucasladeira.repositories.AppUserRepository;
import com.lucasladeira.repositories.RoleRepository;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService{

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public AppUser saveUser(AppUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return appUserRepository.save(user);
	}

	@Override
	public AppUser getUser(String username) {
		Optional<AppUser> user =  appUserRepository.findByUsername(username);
		user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum usuário encontrado!"));	
		return user.get();
	}
	
	@Override
	public List<AppUser> getUsers() {
		return appUserRepository.findAll();
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		Optional<AppUser> user = appUserRepository.findByUsername(username);
		Optional<Role> role = roleRepository.findByName(roleName);
		
		if (user.isPresent() && role.isPresent()) {
			user.get().getRoles().add(role.get());
		}else if (user.isEmpty()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permissão não encontrada");
		}
	}	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = getUser(username);
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>(); //gerando uma colecao de permissoes vazias
		
		user.getRoles().forEach(role -> { 
			authorities.add(new SimpleGrantedAuthority(role.getName())); //gerando uma colecao de SimpleGrantedAuthority
		});																//com base nos roles do Usuario

		//para gerar um usuario é preciso o username, a senha e as permissoes
		return new User(user.getUsername(), user.getPassword(), authorities);
	}
	
}
