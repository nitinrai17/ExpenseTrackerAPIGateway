package com.example.technoxtream17.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.technoxtream17.user.model.User;
import com.example.technoxtream17.user.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public User createUser(User user) {
		
		return userRepo.save(user);
	}
	
	public Boolean isEmailExist(String email) {
		return userRepo.existsByEmail(email);
	}

}
