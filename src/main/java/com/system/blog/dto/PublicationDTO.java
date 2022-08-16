package com.system.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.system.blog.entities.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {
	
	private Long id;
	@NotEmpty
	@Size(min = 3, message = "should have at least 3 characters")
	private String title;
	@NotEmpty
	@Size(min = 10, message = "should have at least 10 characters")
	private String description;
	@NotEmpty
	private String content;
	private Set<Comment> comments;

}
