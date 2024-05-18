package com.fitstam.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
	private Integer postId;
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;
	private String imageUrl;
	private Date postedDate;
	private CategoryDTO category;
	private UserDTO user;
	
	private Set<CommentDTO> comments=new HashSet<CommentDTO>();
}
