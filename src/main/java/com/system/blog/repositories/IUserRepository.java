package com.system.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.system.blog.entities.User;
@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
	public Optional<User>findByEmail(String email);
	public Optional<User> findByUsernameOrEmail(String username,String email);
	public Optional<User> findByUsername(String username);
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);
	
	

}
