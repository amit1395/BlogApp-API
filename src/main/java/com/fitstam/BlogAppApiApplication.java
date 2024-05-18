package com.fitstam;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitstam.constants.AppConstant;
import com.fitstam.entities.Role;
import com.fitstam.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passswordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
		
		//System.out.println(new BCryptPasswordEncoder().encode("1234567"));
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(this.passswordEncoder.encode("ahvv"));
		
		try {
			Role role =new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName(AppConstant.ADMIN);
			
			Role role1 =new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName(AppConstant.NORMAL);
			
			List<Role> roles = List.of(role,role1);
			List<Role> result = this.roleRepo.saveAll(roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
