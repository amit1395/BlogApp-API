package com.fitstam.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fitstam.entities.Role;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDTO {
	private int id;
	@NotEmpty
	@Size(min =4,message="UserName must be of 4 characters !!")
	private String name;
	
	@Email(message="Email address is not valid !")
	private String email;
	
	@NotEmpty
	@Size(min =3,max=10,message="Password must be min of 3 and max of 10 char")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDTO> role=new HashSet<RoleDTO>();

}
