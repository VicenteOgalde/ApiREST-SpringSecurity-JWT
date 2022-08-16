package com.system.blog.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private Long id;
	@NotEmpty(message = "must be not empty")
	private String name;
	@Email
	private String email;
	private String body;
	
}
