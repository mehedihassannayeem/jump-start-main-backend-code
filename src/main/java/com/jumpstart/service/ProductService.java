package com.jumpstart.service;

import java.util.List;

import com.jumpstart.payload.ProductDto;

public interface ProductService {

	// add product
	ProductDto addProduct(ProductDto productDto);

	// get product
	ProductDto getProduct(String proid);

	// get products ---------------------------------------------- pagination
	List<ProductDto> getProducts();

	// update product
	ProductDto updateProduct(ProductDto productDto);

	// delete product
	void deleteProduct(String proid);

	// get products category wise --------------------------------------- pagination
	List<ProductDto> getCategoryProducts(String catId);

	// get products brand wise ---------------------------------------------
	// pagination
	List<ProductDto> getBrandProducts(String brandId);

	// get searched products ---------------------------------------------
	// pagination
	List<ProductDto> getSearchedProducts(String keyword);

	// live toggle

	// stock toggle

	// quantity changing

}
