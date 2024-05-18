package com.fitstam.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
	private int categoryId;
	
	@NotEmpty()
	@Size(max=50,message = "Category name should be max length of 10")
	private String categoryName;
	
	@NotEmpty
	@Size(min=5,message = "Category name should be min length of 5")
	private String catgDesc;
}
