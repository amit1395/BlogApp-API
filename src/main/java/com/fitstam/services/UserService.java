package com.fitstam.services;

import java.util.List;

import com.fitstam.payloads.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO user);
	UserDTO updateUser(UserDTO user,Integer userId);
	UserDTO getUser(Integer userId);
	List<UserDTO> getAllUser();
	void deleteUser(Integer userId);
	UserDTO registerNewUser(UserDTO user);
}
