package com.system.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.blog.dto.CommentDTO;
import com.system.blog.service.ICommentDTOService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private ICommentDTOService commentDTOService;
	
	@GetMapping("/publications/{publicationId}/comments")
	private ResponseEntity<List<CommentDTO>> findByPublicationId(@PathVariable(value = "publicationId")Long publicationId){
		return new ResponseEntity<List<CommentDTO>>(commentDTOService.findCommentByPublicationId(publicationId),HttpStatus.OK);
	}
	@GetMapping("/publications/{publicationId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> findCommentById(@PathVariable(value="publicationId")Long publicationId,
			@PathVariable(value="commentId")Long commentId){
		return new ResponseEntity<CommentDTO>(commentDTOService.findCommentById(publicationId, commentId), HttpStatus.OK);
	}
	
	
	@PostMapping("/publications/{publicationId}/comments")
	public ResponseEntity<CommentDTO> saveComment(
			@PathVariable(value="publicationId")Long publicationId,
			@Valid @RequestBody CommentDTO commentDTO){
		return new ResponseEntity<>(commentDTOService.createComment(publicationId, commentDTO), HttpStatus.CREATED);
	}
	@PutMapping("/publications/{publicationId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateCommentById(@PathVariable(value="publicationId")Long publicationId,
			@PathVariable(value="commentId")Long commentId,@Valid @RequestBody CommentDTO commentDTO){
		return new ResponseEntity<>(commentDTOService.updateComment(publicationId, commentId, commentDTO), HttpStatus.OK);
	}
	@DeleteMapping("/publications/{publicationId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(value="publicationId")Long publicationId,
			@PathVariable(value="commentId")Long commentId){
		commentDTOService.deleteComment(publicationId, commentId);
		return new ResponseEntity<String>("Delete Ok",HttpStatus.OK);
	}
		
}
