package com.jumpstart.service;

import java.util.List;

import com.jumpstart.payload.ProductCategoryDto;

public interface ProductCategoryService {

	// category add
	ProductCategoryDto addCategory(ProductCategoryDto productCategoryDto);

	// categories fetch
	List<ProductCategoryDto> getCategories();

	// category fetch
	ProductCategoryDto getCategory(String catId);

	// category update
	ProductCategoryDto updateCategory(ProductCategoryDto productCategoryDto);

	// category delete
	void deleteCategory(String catId);

	// check category name
	boolean categoryNameExist(String catName);

}
