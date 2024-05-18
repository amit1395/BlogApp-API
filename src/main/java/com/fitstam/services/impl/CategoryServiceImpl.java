package com.fitstam.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitstam.entities.Category;
import com.fitstam.exceptions.ResourceNotFoundException;
import com.fitstam.payloads.CategoryDTO;
import com.fitstam.repositories.CategoryRepo;
import com.fitstam.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo ctgRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO category) {
		Category pojo =this.modelMapper.map(category, Category.class);
		Category addedDto =this.ctgRepo.save(pojo);
		return this.modelMapper.map(addedDto, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO dto, Integer categoryId) {
		Category cat=this.ctgRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category Name", "category Id", categoryId));
		
		cat.setCategoryName(dto.getCategoryName());
		cat.setCatgDesc(dto.getCatgDesc());
		
		Category updatedData = this.ctgRepo.save(cat);
		
		return this.modelMapper.map(updatedData, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category cat=this.ctgRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category Name", "category Id", categoryId));
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> findAll = this.ctgRepo.findAll();
		List<CategoryDTO> collect = findAll.stream().map((cat)->this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.ctgRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category Name", "category Id", categoryId));
		this.ctgRepo.delete(cat);
	}

}
