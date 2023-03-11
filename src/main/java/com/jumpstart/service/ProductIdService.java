package com.jumpstart.service;

import java.util.List;

import com.jumpstart.payload.ProductIdDto;

public interface ProductIdService {

	// add product id
	ProductIdDto addProductId(ProductIdDto productIdDto);

	// get all product id
	List<ProductIdDto> getProductIds();

	// get a product id
	ProductIdDto getProductId(String proid);

	// update product id
	ProductIdDto updateProductId(ProductIdDto productIdDto);

	// delete product id
	void deleteProductId(String proid);

	// check product id
	boolean productIdExist(String proId);

	// check product name
	boolean productNameExist(String proName);

}
