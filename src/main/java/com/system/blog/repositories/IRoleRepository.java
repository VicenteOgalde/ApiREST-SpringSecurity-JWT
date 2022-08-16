package com.system.blog.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.blog.entities.Role;
@Repository
public interface IRoleRepository extends JpaRepository<Role,Long>{
	
	public Optional<Role> findByName(String name);

}
