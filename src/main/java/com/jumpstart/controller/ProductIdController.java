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
import com.jumpstart.payload.ProductIdDto;
import com.jumpstart.service.ProductIdService;

@RestController
@RequestMapping("/api/v1/product-ids")
public class ProductIdController {

	@Autowired
	private ProductIdService productIdService;

	// adding new product id
	@PostMapping("/")
	public ResponseEntity<?> addProductId(@Valid @RequestBody ProductIdDto productIdDto) {

		if (this.productIdService.productIdExist(productIdDto.getProductId())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product id is exist !"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (this.productIdService.productNameExist(productIdDto.getProductName())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product name is exist !"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ProductIdDto productId = this.productIdService.addProductId(productIdDto);

		if (productId == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false, "Something goes wrong while creating new product id!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductIdDto>(productId, HttpStatus.CREATED);
	}

	// getting a product id
	@GetMapping("/{proid}")
	public ResponseEntity<ProductIdDto> getProductId(@PathVariable String proid) {
		return new ResponseEntity<ProductIdDto>(this.productIdService.getProductId(proid), HttpStatus.FOUND);
	}

	// getting all product ids
	@GetMapping("/")
	public ResponseEntity<?> getAllProductIds() {

		List<ProductIdDto> productIds = this.productIdService.getProductIds();

		if (productIds == null || productIds.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "No product id exist! Create first."),
					HttpStatus.OK);
		}

		return new ResponseEntity<List<ProductIdDto>>(productIds, HttpStatus.FOUND);
	}

	// updating product id
	@PutMapping("/")
	public ResponseEntity<?> updateProductId(@Valid @RequestBody ProductIdDto productIdDto) {

		ProductIdDto updatedProductId = this.productIdService.updateProductId(productIdDto);

		if (updatedProductId == null) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false,
							"Something goes wrong while updating product id of " + productIdDto.getProidsl()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductIdDto>(updatedProductId, HttpStatus.CREATED);

	}

	// deleting product id
	@DeleteMapping("/{proid}")
	public ResponseEntity<ApiResponse> deleteProductId(@PathVariable String proid) {
		this.productIdService.deleteProductId(proid);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product id deleted successfully"), HttpStatus.OK);
	}

}
