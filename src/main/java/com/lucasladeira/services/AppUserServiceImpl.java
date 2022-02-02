package com.lucasladeira.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lucasladeira.entities.AppUser;
import com.lucasladeira.entities.Role;
import com.lucasladeira.repositories.AppUserRepository;
import com.lucasladeira.repositories.RoleRepository;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public AppUser saveUser(AppUser user) {
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

}
