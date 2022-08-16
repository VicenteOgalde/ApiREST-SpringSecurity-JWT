package com.system.blog.service;

import java.util.List;

import com.system.blog.dto.CommentDTO;

public interface ICommentDTOService {
	
	public CommentDTO createComment(Long publicationId,CommentDTO commentDTO);
	public List<CommentDTO> findCommentByPublicationId(Long id);
	public CommentDTO findCommentById(Long publicationId, Long commentId);
	public CommentDTO updateComment(Long publicationId, Long commentId, CommentDTO updateCommentDTO);
	public void deleteComment(Long publicationId, Long commentId);
}
