package com.lucasladeira.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasladeira.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer>{
	Optional<AppUser> findByUsername(String username);
}
