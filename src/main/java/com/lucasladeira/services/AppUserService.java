package com.lucasladeira.services;

import java.util.List;

import com.lucasladeira.entities.AppUser;
import com.lucasladeira.entities.Role;

public interface AppUserService {

	AppUser saveUser(AppUser user);
	Role saveRole(Role role);
	AppUser getUser(String username);
	List<AppUser> getUsers();
	void addRoleToUser(String username, String roleName);
	
	//TestConfig
	//AppUser saveUser(List<AppUser> user);
}
