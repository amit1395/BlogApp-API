package com.fitstam.services;

import org.springframework.stereotype.Service;

import com.fitstam.payloads.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(CommentDTO dto,Integer postId);
	
	void deleteComment(Integer commentId);

}
