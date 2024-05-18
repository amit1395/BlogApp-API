package com.fitstam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitstam.exceptions.ApiException;
import com.fitstam.payloads.JwtAuthRequest;
import com.fitstam.payloads.JwtAuthResponse;
import com.fitstam.payloads.UserDTO;
import com.fitstam.security.CustomUserDetailService;
import com.fitstam.security.JwtTokenHelper;
import com.fitstam.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomUserDetailService userDeatilsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
			
		this.authenticate(request.getUserName(),request.getPassword());
		
		UserDetails userDetails = this.userDeatilsService.loadUserByUsername(request.getUserName());
		String token=this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse authResp=new JwtAuthResponse();
		authResp.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(authResp,HttpStatus.OK);
	}
	
	public void authenticate(String username,String password) throws Exception {
		UsernamePasswordAuthenticationToken authclass= new UsernamePasswordAuthenticationToken(username, password);
		try {
			
			this.authenticationManager.authenticate(authclass);
		} catch (BadCredentialsException e) {
			throw new ApiException("BAD credetial");
		}
	}
	
	// register new user
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerNewUser(@RequestBody UserDTO request) throws Exception{
			
		UserDTO registerNewUser = this.userService.registerNewUser(request);
		
		return new ResponseEntity<UserDTO>(registerNewUser,HttpStatus.CREATED);
	}
}
