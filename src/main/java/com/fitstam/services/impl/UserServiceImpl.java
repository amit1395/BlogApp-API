package com.fitstam.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitstam.constants.AppConstant;
import com.fitstam.entities.Role;
import com.fitstam.entities.User;
import com.fitstam.exceptions.ResourceNotFoundException;
import com.fitstam.payloads.UserDTO;
import com.fitstam.repositories.RoleRepo;
import com.fitstam.repositories.UserRepo;
import com.fitstam.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo dao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDTO createUser(UserDTO user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		// TODO Auto-generated method stub
		return this.entityToDto(dao.save(this.dtoToEntity(user)));
	}

	@Override
	public UserDTO updateUser(UserDTO dto, Integer userId) {
		User user=this.dao.findById(userId)
				.orElseThrow((()-> new ResourceNotFoundException("user","id",userId)));
		
		user.setName(dto.getName());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setAbout(dto.getAbout());
		
		User saveUser = this.dao.save(user);
		
		return this.entityToDto(saveUser);
		
	}

	@Override
	public UserDTO getUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.dao.findById(userId)
				.orElseThrow((()-> new ResourceNotFoundException("user","id",userId)));
		return this.entityToDto(user);
	}

	@Override
	public List<UserDTO> getAllUser() {
		
		List<User> users = this.dao.findAll();
		List<UserDTO> collect = users.stream().map(user -> this.entityToDto(user)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.dao.findById(userId)
				.orElseThrow((()-> new ResourceNotFoundException("user","id",userId)));
		dao.delete(user);

	}

	/*
	 * private User dtoToEntity(UserDTO dto) { User user = new User();
	 * user.setId(dto.getId()); user.setName(dto.getName());
	 * user.setEmail(dto.getEmail()); user.setPassword(dto.getPassword());
	 * user.setAbout(dto.getAbout());
	 * 
	 * return user; }
	 * 
	 * private UserDTO entityToDto(User dto) { UserDTO user = new UserDTO();
	 * user.setId(dto.getId()); user.setName(dto.getName());
	 * user.setEmail(dto.getEmail()); user.setPassword(dto.getPassword());
	 * user.setAbout(dto.getAbout());
	 * 
	 * return user; }
	 */
	
	//using ModelMapper to bind dto and pojo classes
	private User dtoToEntity(UserDTO dto) {
		return this.modelMapper.map(dto, User.class);
	}
	
	private UserDTO entityToDto(User dto) {
		return this.modelMapper.map(dto, UserDTO.class);
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		//user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//roles
		Role role = this.roleRepo.findById(AppConstant.NORMAL_USER).get();
		user.getRole().add(role);
		User newUser = this.dao.save(user);
		return this.modelMapper.map(newUser, UserDTO.class);
	}
	
}
