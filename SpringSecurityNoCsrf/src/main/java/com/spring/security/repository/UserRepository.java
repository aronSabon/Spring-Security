package com.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);

}
