package com.system.blog.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.blog.dto.LoginDTO;
import com.system.blog.dto.RegisterDTO;
import com.system.blog.entities.Role;
import com.system.blog.entities.User;
import com.system.blog.repositories.IRoleRepository;
import com.system.blog.repositories.IUserRepository;
import com.system.blog.security.JWTAuthResponseDTO;
import com.system.blog.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
		
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//get token from jwttokenprovider
		String token = jwtTokenProvider.tokenGenerator(authentication);
		
		return ResponseEntity.ok(new JWTAuthResponseDTO(token));
	}
	@PostMapping("/register")
	public ResponseEntity<?> userRegister(@RequestBody RegisterDTO registerDTO){
		if(userRepository.existsByUsername(registerDTO.getName())) {
			return new ResponseEntity<>("username already exists", HttpStatus.BAD_REQUEST);
		}
		if(userRepository.existsByEmail(registerDTO.getEmail())) {
			return new ResponseEntity<>("email already exists", HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		user.setName(registerDTO.getName());
		user.setUsername(registerDTO.getUsername());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		Role roles = roleRepository.findByName("ROLE_USER").get();
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		return new ResponseEntity<>("user register successful",HttpStatus.CREATED);
		
	}
}
