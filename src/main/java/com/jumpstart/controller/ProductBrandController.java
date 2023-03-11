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
import com.jumpstart.payload.ProductBrandDto;
import com.jumpstart.service.ProductBrandService;

@RestController
@RequestMapping("/api/v1/product-brands")
public class ProductBrandController {

	@Autowired
	private ProductBrandService productBrandService;

	// adding new brand
	@PostMapping("/")
	public ResponseEntity<?> addProductBrand(@Valid @RequestBody ProductBrandDto productBrandDto) {

		if (this.productBrandService.brandNameExist(productBrandDto.getBrandName())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Brand name is exist !"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ProductBrandDto productdBrand = this.productBrandService.addProductBrand(productBrandDto);

		if (productdBrand == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false, "Something goes wrong while creating new product brand!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductBrandDto>(productdBrand, HttpStatus.CREATED);
	}

	// getting brand
	@GetMapping("/{brandid}")
	public ResponseEntity<ProductBrandDto> getProductId(@PathVariable String brandid) {
		return new ResponseEntity<ProductBrandDto>(this.productBrandService.getProductBrand(brandid), HttpStatus.FOUND);
	}

	// getting brands
	@GetMapping("/")
	public ResponseEntity<?> getAllProductIds() {

		List<ProductBrandDto> productBrandDtos = this.productBrandService.getProductBrands();

		if (productBrandDtos == null || productBrandDtos.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "No product brand exist! Create first."),
					HttpStatus.OK);
		}

		return new ResponseEntity<List<ProductBrandDto>>(productBrandDtos, HttpStatus.FOUND);
	}

	// updating brand
	@PutMapping("/")
	public ResponseEntity<?> updateProductId(@Valid @RequestBody ProductBrandDto productBrandDto) {

		if (this.productBrandService.brandNameExist(productBrandDto.getBrandName())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Brand name is exist !"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ProductBrandDto updatedProductBrandDtod = this.productBrandService.updateProductBrand(productBrandDto);

		if (updatedProductBrandDtod == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false,
							"Something goes wrong while updating product brand of " + productBrandDto.getProbndsl()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductBrandDto>(updatedProductBrandDtod, HttpStatus.CREATED);

	}

	// deleting brand
	@DeleteMapping("/{brandid}")
	public ResponseEntity<ApiResponse> deleteProductId(@PathVariable String brandid) {
		this.productBrandService.deleteProductBrand(brandid);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product brand deleted successfully"),
				HttpStatus.OK);
	}

}
