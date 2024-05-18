package com.fitstam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitstam.payloads.ApiResponses;
import com.fitstam.payloads.CommentDTO;
import com.fitstam.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
	private CommentService service;
	
	@PostMapping("/post/{postId}/saveComment")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO dto,@PathVariable("postId") Integer postId){
		CommentDTO createComment = this.service.createComment(dto, postId);
		return new ResponseEntity<CommentDTO>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteComm/{commentId}")
	public ResponseEntity<ApiResponses> deleteComment(@PathVariable("commentId") Integer commentId){
		this.service.deleteComment(commentId);
		return new ResponseEntity<ApiResponses>(new ApiResponses("Comment deleted successfully",true),HttpStatus.OK);
		
		
	}

}
