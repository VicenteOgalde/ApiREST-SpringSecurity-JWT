package com.system.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.blog.entities.Publication;
@Repository
public interface IPublicationRepository extends JpaRepository<Publication, Long>{

}
