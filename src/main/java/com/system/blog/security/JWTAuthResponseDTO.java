package com.system.blog.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponseDTO {
	
	private String accessToken;
	private String tokenType="Bearer";
	public JWTAuthResponseDTO(String accessToken) {
		super();
		this.accessToken = accessToken;
	}
	
	

}
