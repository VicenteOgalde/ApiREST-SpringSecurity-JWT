package com.system.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.system.blog.entities.Role;
import com.system.blog.entities.User;
import com.system.blog.repositories.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(()-> new UsernameNotFoundException("user not found with that user or email "+usernameOrEmail)); 
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRoles(user.getRoles()));
	}
	private Collection<? extends GrantedAuthority> mapRoles(Set<Role> set){
		return set.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
