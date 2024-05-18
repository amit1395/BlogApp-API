package com.fitstam.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitstam.payloads.ApiResponses;
import com.fitstam.payloads.CategoryDTO;
import com.fitstam.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService catservice;
	
	//create
	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO dto){
		CategoryDTO returnDTO = this.catservice.createCategory(dto);
		return new ResponseEntity<CategoryDTO>(returnDTO,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/updateCategory/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO dto,@PathVariable Integer catId){
		CategoryDTO returnDTO = this.catservice.updateCategory(dto, catId);
		return new ResponseEntity<CategoryDTO>(returnDTO,HttpStatus.OK);
	}
	//delete
	@DeleteMapping("/deleteCategory/{catId}")
	public ResponseEntity<ApiResponses> deleteCategory(@PathVariable Integer catId){
			this.catservice.deleteCategory(catId);
		return new ResponseEntity<ApiResponses>(new ApiResponses("category is deleted successfully",true),HttpStatus.OK);
	}
	//getsingle user
	@GetMapping("/getSingleCategory/{catId}")
	public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable Integer catId){
		CategoryDTO returnDTO = this.catservice.getCategory(catId);
		return new ResponseEntity<CategoryDTO>(returnDTO,HttpStatus.OK);
	}
	//getalluser
	@GetMapping("/getAllCategory")
	public ResponseEntity<List<CategoryDTO>> getAllCategory(){
		List<CategoryDTO> allCategory = this.catservice.getAllCategory();
		return new ResponseEntity<List<CategoryDTO>>(allCategory,HttpStatus.OK);
	}

}
