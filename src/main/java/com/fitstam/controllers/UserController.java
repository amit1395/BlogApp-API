package com.fitstam.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitstam.payloads.ApiResponses;
import com.fitstam.payloads.UserDTO;
import com.fitstam.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	//POST Create USER
	@PostMapping("/createUser")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto){
		UserDTO userDto= this.service.createUser(dto);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}
	
	//PUT Update User
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO dto,@PathVariable("userId") Integer userId){
		UserDTO updateUser = this.service.updateUser(dto, userId);
		return new ResponseEntity<>(updateUser,HttpStatus.OK);
	}
	
	//DELETE User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiResponses> deleteUser(@PathVariable("userId") Integer userId){
		this.service.deleteUser(userId);
		//return new ResponseEntity(Map.of("Message" ,"user deleted Successfully"),HttpStatus.OK);		
		return new ResponseEntity(new ApiResponses("user deleted Successfully",true),HttpStatus.OK);
	}
	
	//GET All Users
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUser(){
		return ResponseEntity.ok(this.service.getAllUser());
	}
	
	//GET single Users
		@GetMapping("/getUser/{userId}")
		public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Integer userId ){
			return ResponseEntity.ok(this.service.getUser(userId));
		}
	
	
}
