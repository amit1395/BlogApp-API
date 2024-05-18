package com.fitstam.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fitstam.entities.Category;
import com.fitstam.entities.Post;
import com.fitstam.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);

	Slice<Post> findByCategory(Category category, Pageable page);
	
	Slice<Post> findByUser(User user, Pageable page);
	
	List<Post> findByTitleContaining(String title);

}
