package com.fitstam.services;

import java.util.List;

import com.fitstam.payloads.CategoryDTO;

public interface CategoryService {
	CategoryDTO createCategory(CategoryDTO category);
	CategoryDTO updateCategory(CategoryDTO category,Integer categoryId);
	CategoryDTO getCategory(Integer categoryId);
	List<CategoryDTO> getAllCategory();
	void deleteCategory(Integer categoryId);
}
