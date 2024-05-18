package com.fitstam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitstam.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
