package com.jumpstart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entities.ProductCategory;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.payload.ProductCategoryDto;
import com.jumpstart.repository.ProductCategoryRepository;
import com.jumpstart.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductCategoryDto addCategory(ProductCategoryDto productCategoryDto) {
		ProductCategory productCategory = this.modelMapper.map(productCategoryDto, ProductCategory.class);

		// setting the primary key
		productCategory.setProcatsl(UUID.randomUUID().toString());

		ProductCategory savedCategory = this.productCategoryRepository.save(productCategory);

		return this.modelMapper.map(savedCategory, ProductCategoryDto.class);
	}

	@Override
	public List<ProductCategoryDto> getCategories() {
		List<ProductCategory> produccCategories = this.productCategoryRepository.findAll();

		List<ProductCategoryDto> productCategoryDtos = produccCategories.stream()
				.map(category -> this.modelMapper.map(category, ProductCategoryDto.class)).collect(Collectors.toList());
		return productCategoryDtos;
	}

	@Override
	public ProductCategoryDto getCategory(String catId) {
		ProductCategory productCategory = this.productCategoryRepository.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "provided category id", catId));

		return this.modelMapper.map(productCategory, ProductCategoryDto.class);
	}

	@Override
	public ProductCategoryDto updateCategory(ProductCategoryDto productCategoryDto) {
		ProductCategory oldProductCategory = this.productCategoryRepository.findById(productCategoryDto.getProcatsl())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "provided category id",
						productCategoryDto.getProcatsl()));

		oldProductCategory.setCatName(productCategoryDto.getCatName());
		oldProductCategory.setCatIcon(productCategoryDto.getCatIcon());

		ProductCategory updatedProductCategory = this.productCategoryRepository.save(oldProductCategory);

		return this.modelMapper.map(updatedProductCategory, ProductCategoryDto.class);

	}

	@Override
	public void deleteCategory(String catId) {
		ProductCategory productCategory = this.productCategoryRepository.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "provided category id", catId));

		this.productCategoryRepository.delete(productCategory);

	}

	@Override
	public boolean categoryNameExist(String catName) {
		return this.productCategoryRepository.existsByCatName(catName);
	}

}
