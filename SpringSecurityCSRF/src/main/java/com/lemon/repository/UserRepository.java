package com.lemon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemon.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);

}
