package com.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.model.User;
import com.spring.security.model.UserPrincipal;
import com.spring.security.repository.UserRepository;
@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username);
		System.out.println(user);
		System.out.println("usrname: "+ username);
		if(user==null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException("usernot found");
		}
		
		
		return new UserPrincipal(user);
	}

}
