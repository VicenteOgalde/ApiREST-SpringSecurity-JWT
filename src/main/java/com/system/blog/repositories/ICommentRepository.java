package com.system.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.blog.entities.Comment;
@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long>{

	public List<Comment> findByPublicationId(Long publicationId);
}
