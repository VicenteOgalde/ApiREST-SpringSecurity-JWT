package com.system.blog.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.system.blog.exceptions.BlogAppException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-miliseconds}")
	private int jwtExpirationInMs;
	
	public String tokenGenerator(Authentication authentication) {
		
		String username = authentication.getName();
		Date actualDate = new Date();
		Date expirationDate= new Date(actualDate.getTime() + jwtExpirationInMs);
		
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		return token;
	}
	
	public String usernameObtainOfJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	public boolean tokenValidate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch(SignatureException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Not valid JWT signature");
			
		}catch(MalformedJwtException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Not valid JWT token");
		}catch(ExpiredJwtException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		}catch(UnsupportedJwtException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Not supported JWT token");
		}catch(IllegalArgumentException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Claims JWT empty");
		}
		
	}
}
