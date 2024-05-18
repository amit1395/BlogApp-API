package com.fitstam.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitstam.constants.AppConstant;
import com.fitstam.payloads.ApiResponses;
import com.fitstam.payloads.PostDTO;
import com.fitstam.payloads.PostResponse;
import com.fitstam.services.FileService;
import com.fitstam.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService service;
	
	@Autowired
	private FileService fileService;
	
	@Value("images")
	private String path;
	
	
	//createPost
	@PostMapping("/user/{userId}/category/{categoryId}/savePost")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		PostDTO createPost = service.createPost(dto, userId, categoryId);
		return new ResponseEntity<PostDTO>(createPost,HttpStatus.CREATED);
		
	}
	
	//getByCategory
	@GetMapping("/category/{categoryId}/getBycategory")
	public ResponseEntity<List<PostDTO>> getPostSByCategory(@PathVariable Integer categoryId){
		
		List<PostDTO> postsByCategory = this.service.getPostsByCategory(categoryId);
		
		return new ResponseEntity<List<PostDTO>>(postsByCategory,HttpStatus.OK);
		
	}
	
	//getByCategory pagination
		@GetMapping("/category/{categoryId}/getBycategoryPagination")
		public ResponseEntity<PostResponse> getPostSByCategoryPagination(@PathVariable Integer categoryId,
				@RequestParam(value="pageNum",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNum,
				@RequestParam(value="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
				@RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
				@RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir
				){
			
			PostResponse postsByCategoryPagination = this.service.getPostsByCategoryPagination(categoryId, pageNum, pageSize,sortBy,sortDir);
			
			return new ResponseEntity<PostResponse>(postsByCategoryPagination,HttpStatus.OK);
			
		}
	
	//getByUser
		@GetMapping("/user/{userId}/getByUser")
		public ResponseEntity<List<PostDTO>> getPostSByUser(@PathVariable Integer userId){
			
			List<PostDTO> postsByUser = this.service.getPostsByUser(userId);
			
			return new ResponseEntity<List<PostDTO>>(postsByUser,HttpStatus.OK);
			
		}
		
		//getByUser
				@GetMapping("/user/{userId}/getByUserPagination")
				public ResponseEntity<PostResponse> getPostSByUserPagination(@PathVariable Integer userId,
						@RequestParam(value="pageNum",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNum,
						@RequestParam(value="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
						@RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
						@RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir
						){
					
					PostResponse postsByCategoryPagination = this.service.getPostsByUserPagination(userId, pageNum, pageSize,sortBy,sortDir);
					
					return new ResponseEntity<PostResponse>(postsByCategoryPagination,HttpStatus.OK);
					
				}
		
	//get all posts
	@GetMapping("/getAllPost")	
	public ResponseEntity<List<PostDTO>> getAllPosts(){
		List<PostDTO> allPost = this.service.getAllPost();
		return new ResponseEntity<List<PostDTO>>(allPost,HttpStatus.OK);
	}
	
	//implementing pagination and sorting
	@GetMapping("/getAllPostByPagination")	
	public ResponseEntity<PostResponse> getAllPostsByPagination(
			@RequestParam(value="pageNum",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNum,
			@RequestParam(value="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir
			
			){
		 PostResponse allPostsByPagination = this.service.getAllPostsByPagination(pageNum,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPostsByPagination,HttpStatus.OK);
	}
	
	//get single Post
	@GetMapping("/getPost/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
		PostDTO postById = this.service.getPostById(postId);
		return new ResponseEntity<PostDTO>(postById,HttpStatus.OK);
	}
	
	//delete Post
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<ApiResponses> deletePost(@PathVariable Integer postId){
		this.service.deletePost(postId);
		return new ResponseEntity<ApiResponses>(new ApiResponses("Post deleted successfully",true),HttpStatus.OK);
	}
	
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto,@PathVariable Integer postId){
		PostDTO createPost = service.updatePost(dto, postId);
		return new ResponseEntity<PostDTO>(createPost,HttpStatus.CREATED);
		
	}
	
	//search posts
	@GetMapping("/searchPost/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPost(@PathVariable String keyword){
		List<PostDTO> postsByKeywords = this.service.getPostsByKeywords(keyword);
		return new ResponseEntity<List<PostDTO>>(postsByKeywords,HttpStatus.OK);
	}
	
	//upload Image
	@PostMapping("/uploadImage/{postId}")
	public ResponseEntity<PostDTO> uploadImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		PostDTO postDto = this.service.getPostById(postId);
		
		String uploadImage = this.fileService.uploadImage(path, image);
		
		postDto.setImageUrl(uploadImage);
		PostDTO updatePost = this.service.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping(value="/getImage/{imageName}",produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
		
			) throws IOException{
		InputStream resource=this.fileService.getResourcce(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
	

}
