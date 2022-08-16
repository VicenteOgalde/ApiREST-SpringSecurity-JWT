package com.system.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.system.blog.dto.CommentDTO;
import com.system.blog.entities.Comment;
import com.system.blog.entities.Publication;
import com.system.blog.exceptions.BlogAppException;
import com.system.blog.exceptions.ResourceNotFoundException;
import com.system.blog.repositories.ICommentRepository;
import com.system.blog.repositories.IPublicationRepository;

@Service
public class CommentDTOServiceImpl implements ICommentDTOService {

	@Autowired
	private ICommentRepository commentRepository;
	@Autowired
	private IPublicationRepository publicationRepository;


	@Override
	public CommentDTO createComment(Long publicationId, CommentDTO commentDTO) {
		Comment comment = mapCommentEntity(commentDTO);
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
		comment.setPublication(publication);

		return mapDTO(commentRepository.save(comment));
	}

	// transform entity to DTO
	private CommentDTO mapDTO(Comment comment) {
		
		CommentDTO responseCommentDTO = new CommentDTO();
	
		responseCommentDTO.setId(comment.getId());
		responseCommentDTO.setName(comment.getName());
		responseCommentDTO.setEmail(comment.getEmail());
		responseCommentDTO.setBody(comment.getBody());

		return responseCommentDTO;
	}

	// transform DTO to Entity
	private Comment mapCommentEntity(CommentDTO commentDTO) {
		Comment comment = new Comment();
		
		comment.setName(commentDTO.getName());
		comment.setEmail(commentDTO.getEmail());
		comment.setBody(commentDTO.getBody());
		return comment;
	}

	@Override
	public List<CommentDTO> findCommentByPublicationId(Long publicationId) {
		List<Comment> comments = commentRepository.findByPublicationId(publicationId);
		return comments.stream().map(comment -> mapDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDTO findCommentById(Long publicationId, Long commentId) {
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		if(!publication.getId().equals(comment.getPublication().getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "comment does not belong to the publication");
		}
		return mapDTO(comment);
	}

	@Override
	public CommentDTO updateComment(Long publicationId, Long commentId, CommentDTO updateCommentDTO) {
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		if(!publication.getId().equals(comment.getPublication().getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "comment does not belong to the publication");
		}
		
		comment.setName(updateCommentDTO.getName());
		comment.setEmail(updateCommentDTO.getEmail());
		comment.setBody(updateCommentDTO.getBody());
		
		return mapDTO(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long publicationId, Long commentId) {
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		if(!publication.getId().equals(comment.getPublication().getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "comment does not belong to the publication");
		}
		commentRepository.delete(comment);
		
	}

}
