package com.system.blog.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.system.blog.dto.DetailsError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<DetailsError> handlerResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {
		DetailsError detailsError= new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(detailsError, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(BlogAppException.class)
	public ResponseEntity<DetailsError> handlerBlogAppException(BlogAppException exception,
			WebRequest webRequest) {
		DetailsError detailsError= new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(detailsError, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<DetailsError> handlerGlobalException(Exception exception,
			WebRequest webRequest) {
		DetailsError detailsError= new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(detailsError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> errors= new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			String fieldName= ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});;
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}


}
