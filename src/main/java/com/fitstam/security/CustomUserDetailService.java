package com.fitstam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fitstam.entities.User;
import com.fitstam.exceptions.ResourceNotFoundException;
import com.fitstam.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load userdetails by email from userepo
		User user = userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user", "with email", username));
		
		return user;
	}
	

}
