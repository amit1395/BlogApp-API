package com.fitstam.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fitstam.entities.Category;
import com.fitstam.entities.Post;
import com.fitstam.entities.User;
import com.fitstam.exceptions.PostNotFoundException;
import com.fitstam.exceptions.ResourceNotFoundException;
import com.fitstam.payloads.PostDTO;
import com.fitstam.payloads.PostResponse;
import com.fitstam.repositories.CategoryRepo;
import com.fitstam.repositories.PostRepo;
import com.fitstam.repositories.UserRepo;
import com.fitstam.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo catRepo;
	
	@Override
	public PostDTO createPost(PostDTO postDto,Integer userId,Integer catgId) {
		
		//get user
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "User Id", userId));
		
		//get category
		
		Category category = this.catRepo.findById(catgId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", catgId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageUrl("default.png");
		post.setPostedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post savePost = this.repo.save(post);
		
		return this.modelMapper.map(savePost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, Integer postId) {
		Post pojo = this.repo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post id", postId));
		pojo.setTitle(postDto.getTitle());
		pojo.setContent(postDto.getContent());
		pojo.setImageUrl(postDto.getImageUrl());
		Post save = this.repo.save(pojo);	
		
		return this.modelMapper.map(save,PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post pojo = this.repo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post id", postId));
		this.repo.delete(pojo);
	}

	@Override
	public List<PostDTO> getAllPost() {

		List<Post> allPosts = this.repo.findAll();
		List<PostDTO> collect = allPosts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		if(collect != null && collect.size()==0)
			throw new PostNotFoundException("posts"," ",0);
		
		return collect;
	}
	//implemneted pagination 	
public PostResponse getAllPostsByPagination(Integer pageNum,Integer pageSize,String sortBy,String sortDir){
		
	PostResponse response=new PostResponse();
	Sort sort= (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
	Pageable page=PageRequest.of(pageNum, pageSize, sort);
	Page<Post> pageable = this.repo.findAll(page);
	List<Post> allPosts = pageable.getContent();
	List<PostDTO> collect = allPosts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	
	if(collect != null && collect.size()==0)
		throw new PostNotFoundException("posts"," ",0);
	else {
		response.setContent(collect);
		response.setPageNum(pageable.getNumber());
		response.setPageSize(pageable.getSize());
		response.setLastPage(pageable.isLast());
		response.setTotalElements(pageable.getTotalElements());
		response.setTotalPages(pageable.getTotalPages());
	}
	
	return response;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post pojo = this.repo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post id", postId));
		return this.modelMapper.map(pojo, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		//get category
		Category category = this.catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
		List<Post> categoryList = this.repo.findByCategory(category);
		
		if(categoryList != null && categoryList.size()==0)
			throw new PostNotFoundException("Posts", "category Id", categoryId);
		
		return categoryList.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	}
	

	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {
		//get user
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "User Id", userId));
		List<Post> userList = this.repo.findByUser(user);
		if(userList!=null && userList.size() == 0)
			throw new PostNotFoundException("Posts", "user Id", userId);
		return userList.stream().map((post)-> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> getPostsByKeywords(String keywoord) {
		List<Post> lists = this.repo.findByTitleContaining(keywoord);
		List<PostDTO> collect = lists.stream().map((post)-> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public PostResponse getPostsByCategoryPagination(Integer categoryId, Integer pageNum, Integer pageSize,String sortBy,String sortDir) {
		PostResponse response=new PostResponse();
		Category category = this.catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
		Sort sort= (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable page=PageRequest.of(pageNum, pageSize, sort);
		Slice<Post> pageable = this.repo.findByCategory(category,page);
		List<Post> categoryList = pageable.getContent();
		
		
		if(categoryList != null && categoryList.size()==0)
			throw new PostNotFoundException("Posts", "category Id", categoryId);
		else {
			response.setContent(categoryList.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList()));
			response.setPageNum(pageable.getNumber());
			response.setPageSize(pageable.getSize());
			response.setLastPage(pageable.isLast());
			response.setTotalElements(pageable.getNumberOfElements());
			//response.setTotalPages(pageable.getTotalPages());
		}
		
		 ; 
		return response;
	}

	@Override
	public PostResponse getPostsByUserPagination(Integer userId, Integer pageNum, Integer pageSize,String sortBy,String sortDir) {
		PostResponse response=new PostResponse();
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Sort sort= (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable page=PageRequest.of(pageNum, pageSize,sort);
		Slice<Post> pageable = this.repo.findByUser(user,page);
		List<Post> categoryList = pageable.getContent();
		
		
		if(categoryList != null && categoryList.size()==0)
			throw new PostNotFoundException("User", "userId", userId);
		else {
			response.setContent(categoryList.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList()));
			response.setPageNum(pageable.getNumber());
			response.setPageSize(pageable.getSize());
			response.setLastPage(pageable.isLast());
			response.setTotalElements(pageable.getNumberOfElements());
			//response.setTotalPages(pageable.getTotalPages());
		}
		
		return response;
	}

}
