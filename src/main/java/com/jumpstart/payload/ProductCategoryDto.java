package com.jumpstart.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductCategoryDto {

	private String procatsl;

	@NotEmpty(message = "Category name can not be empty")
	private String catName;

	@NotEmpty(message = "Category icon can not be empty")
	private String catIcon;

}
