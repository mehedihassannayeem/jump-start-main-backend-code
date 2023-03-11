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
import com.jumpstart.payload.ProductDto;
import com.jumpstart.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// adding product
	@PostMapping("/")
	public ResponseEntity<?> addProductId(@Valid @RequestBody ProductDto productDto) {

		ProductDto product = this.productService.addProduct(productDto);

		if (product == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false, "Something goes wrong while creating new product !"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductDto>(product, HttpStatus.CREATED);
	}

	// getting product
	@GetMapping("/{proid}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String proid) {
		return new ResponseEntity<ProductDto>(this.productService.getProduct(proid), HttpStatus.FOUND);
	}

	// getting products
	@GetMapping("/")
	public ResponseEntity<?> getProducts() {

		List<ProductDto> products = this.productService.getProducts();

		if (products == null || products.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "No product exist! Create first."),
					HttpStatus.OK);
		}

		return new ResponseEntity<List<ProductDto>>(products, HttpStatus.FOUND);
	}

	// updating product
	@PutMapping("/")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDto productDto) {

		ProductDto updatedProduct = this.productService.updateProduct(productDto);

		if (updatedProduct == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false, "Something goes wrong while updating product of " + productDto.getProsl()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.CREATED);

	}

	// deleting product
	@DeleteMapping("/{proid}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String proid) {
		this.productService.deleteProduct(proid);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product deleted successfully"), HttpStatus.OK);
	}

	// category wise

	// brand wise

	// product id wise

}
