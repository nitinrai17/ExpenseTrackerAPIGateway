package com.example.technoxtream17.user.security;

import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.technoxtream17.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails{
	
	private Long id;
	
	private String email;
	
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private Map<String, Object> attributes;
	
	
	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("ROLE_USER")); 
		
		return new UserPrincipal(user.getId(),user.getEmail(),user.getPassword(),
				authorities, null);
	}
	
	
	public static UserPrincipal create(User user, Map<String, Object> attributes ) {
		 
		UserPrincipal userPrincipal = create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
