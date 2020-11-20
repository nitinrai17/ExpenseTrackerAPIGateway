package com.example.technoxtream17.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.technoxtream17.user.error.ResourceNotFoundExcpetion;
import com.example.technoxtream17.user.model.User;
import com.example.technoxtream17.user.repository.UserRepo;
import com.example.technoxtream17.user.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return UserPrincipal.create(user);
	}

	public UserDetails loadUserbyId(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExcpetion("User", "id", id));
		return UserPrincipal.create(user);

	}

}
