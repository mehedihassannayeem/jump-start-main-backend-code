package com.jumpstart.service;

import com.jumpstart.payload.ProductDto;

public interface ProductService {

	// add product
	void addProduct(ProductDto productDto);

	// get product
	void getProduct(String proid);

	// get products ---------------------------------------------- pagination
	void getProducts();

	// update product
	void updateProduct(ProductDto productDto);

	// delete product
	void deleteProduct(String proid);

	// get products category wise --------------------------------------- pagination
	void getCategoryProducts(String catId);

	// get products brand wise ---------------------------------------------
	// pagination
	void getBrandProducts(String brandId);

	// get searched products ---------------------------------------------
	// pagination
	void getSearchedProducts(String keyword);

}
