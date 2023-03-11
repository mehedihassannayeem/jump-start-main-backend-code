package com.jumpstart.service;

import java.util.List;

import com.jumpstart.payload.ProductBrandDto;

public interface ProductBrandService {

	// add product brand
	ProductBrandDto addProductBrand(ProductBrandDto productBrandDto);

	// get all product brand
	List<ProductBrandDto> getProductBrands();

	// get a product brand
	ProductBrandDto getProductBrand(String probdid);

	// update product brand
	ProductBrandDto updateProductBrand(ProductBrandDto productBrandDto);

	// delete product brand
	void deleteProductBrand(String probdid);

	// check brand name
	boolean brandNameExist(String brandName);

}
