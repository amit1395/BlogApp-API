package com.fitstam.services;

import java.util.List;

import com.fitstam.payloads.PostDTO;
import com.fitstam.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDTO createPost(PostDTO postDto,Integer userId,Integer catgId);
	
	//update
	PostDTO updatePost(PostDTO postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all Post
	//modifed to implement searching and soorting pagination
	List<PostDTO> getAllPost();
	
	//get all Post
	//modifed to implement searching and soorting pagination
	PostResponse getAllPostsByPagination(Integer pageNum,Integer pageSize,String sortBy,String sortDir);
	
	//get Single Post
	
	PostDTO getPostById(Integer postId);
	
	//get all post by categoryId
	
	List<PostDTO> getPostsByCategory(Integer categoryId);
	
	//modifed to implement searching and soorting pagination
	PostResponse getPostsByCategoryPagination(Integer categoryId,Integer pageNum,Integer pageSize,String sortBy,String sortDir);
	
	//get all post by user
	
	List<PostDTO> getPostsByUser(Integer userId);
	
	//modifed to implement searching and soorting pagination
	PostResponse getPostsByUserPagination(Integer userId,Integer pageNum,Integer pageSize,String sortBy,String sortDir);
	
	//get post by keywords
	List<PostDTO> getPostsByKeywords(String keywoord);
	

}
