package com.system.blog.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsError {

	private Date timeStamp;
	private String message;
	private String details;
}
