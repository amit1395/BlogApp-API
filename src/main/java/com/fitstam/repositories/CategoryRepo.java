package com.fitstam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitstam.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
