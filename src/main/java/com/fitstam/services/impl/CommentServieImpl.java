package com.fitstam.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitstam.entities.Comment;
import com.fitstam.entities.Post;
import com.fitstam.exceptions.ResourceNotFoundException;
import com.fitstam.payloads.CommentDTO;
import com.fitstam.repositories.CommentRepo;
import com.fitstam.repositories.PostRepo;
import com.fitstam.services.CommentService;

@Service
public class CommentServieImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComment(CommentDTO dto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		
		Comment map = this.modelMapper.map(dto, Comment.class);
		map.setPost(post);
		Comment save = this.commentRepo.save(map);
		
		return this.modelMapper.map(save, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId));
		this.commentRepo.delete(comment);
	}

}
