package com.fitstam.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fitstam.payloads.ApiResponses;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponses> resourceNotFoundExceptionHandler(ResourceNotFoundException exp) {
		String message=exp.getMessage();
		ApiResponses  res=new ApiResponses(message,false);
		return new ResponseEntity<ApiResponses>(res,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp){
		Map<String , String> resp= new HashMap<String, String>();
		exp.getBindingResult()
			.getAllErrors()
			.forEach((error)->{
			  resp.put(((FieldError)error).getField(), error.getDefaultMessage());
			  //here we are typecasting the error to fielderror and getting the fieldname and also message for value
			});
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ApiResponses> postNotFoundException(PostNotFoundException exp){
		String message=exp.getMessage();
		ApiResponses res=new ApiResponses(message,false);
		return new ResponseEntity<ApiResponses>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponses> apiException(ApiException exp){
		String message=exp.getMessage();
		ApiResponses res=new ApiResponses(message,false);
		return new ResponseEntity<ApiResponses>(res,HttpStatus.BAD_REQUEST);
	}

}
