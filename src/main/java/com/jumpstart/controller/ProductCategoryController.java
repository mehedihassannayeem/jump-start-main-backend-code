package com.jumpstart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.payload.ApiResponse;
import com.jumpstart.payload.ProductCategoryDto;
import com.jumpstart.service.ProductCategoryService;

@RestController
@RequestMapping("/api/v1/product-categories")
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	// adding a new category
	@PostMapping("/")
	public ResponseEntity<?> addCategory(@Valid @RequestBody ProductCategoryDto productCategoryDto) {

		if (this.productCategoryService.categoryNameExist(productCategoryDto.getCatName())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category name is exist !"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ProductCategoryDto categoryDto = this.productCategoryService.addCategory(productCategoryDto);

		if (categoryDto == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false, "Something goes wrong while creating new category!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductCategoryDto>(categoryDto, HttpStatus.CREATED);
	}

	// getting a category
	@GetMapping("/{catid}")
	public ResponseEntity<ProductCategoryDto> getCategory(@PathVariable String catid) {
		return new ResponseEntity<ProductCategoryDto>(this.productCategoryService.getCategory(catid), HttpStatus.FOUND);
	}

	// get all categories
	@GetMapping("/")
	public ResponseEntity<?> getAllCategories() {

		List<ProductCategoryDto> categoryDtos = this.productCategoryService.getCategories();

		if (categoryDtos == null || categoryDtos.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "No category exist! Create first."),
					HttpStatus.OK);
		}

		return new ResponseEntity<List<ProductCategoryDto>>(categoryDtos, HttpStatus.FOUND);
	}

	// updating category
	@PutMapping("/")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody ProductCategoryDto productCategoryDto) {

		if (this.productCategoryService.categoryNameExist(productCategoryDto.getCatName())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category name is exist !"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ProductCategoryDto updatedcCategoryDto = this.productCategoryService.updateCategory(productCategoryDto);

		if (updatedcCategoryDto == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false,
							"Something goes wrong while updating category of " + productCategoryDto.getProcatsl()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductCategoryDto>(updatedcCategoryDto, HttpStatus.CREATED);

	}

	// deleting category
	@DeleteMapping("/{catid}")
	public ResponseEntity<ApiResponse> deleteProductId(@PathVariable String catid) {
		this.productCategoryService.deleteCategory(catid);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category deleted successfully"), HttpStatus.OK);
	}

}
