package com.jumpstart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entities.ProductBrand;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.payload.ProductBrandDto;
import com.jumpstart.repository.ProductBrandRepository;
import com.jumpstart.service.ProductBrandService;

@Service
public class ProductBrandServiceImpl implements ProductBrandService {

	@Autowired
	private ProductBrandRepository productBrandRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductBrandDto addProductBrand(ProductBrandDto productBrandDto) {
		ProductBrand productBrand = this.modelMapper.map(productBrandDto, ProductBrand.class);

		// setting the primary key
		productBrand.setProbndsl(UUID.randomUUID().toString());

		ProductBrand savedProductBrand = this.productBrandRepository.save(productBrand);

		return this.modelMapper.map(savedProductBrand, ProductBrandDto.class);
	}

	@Override
	public List<ProductBrandDto> getProductBrands() {
		List<ProductBrand> productBrands = this.productBrandRepository.findAll();

		List<ProductBrandDto> productBrandDtos = productBrands.stream()
				.map(brand -> this.modelMapper.map(brand, ProductBrandDto.class)).collect(Collectors.toList());
		return productBrandDtos;
	}

	@Override
	public ProductBrandDto getProductBrand(String probdid) {
		ProductBrand productBrand = this.productBrandRepository.findById(probdid)
				.orElseThrow(() -> new ResourceNotFoundException("Product brand", "provided id", probdid));

		return this.modelMapper.map(productBrand, ProductBrandDto.class);
	}

	@Override
	public ProductBrandDto updateProductBrand(ProductBrandDto productBrandDto) {
		ProductBrand oldProductBrand = this.productBrandRepository.findById(productBrandDto.getProbndsl()).orElseThrow(
				() -> new ResourceNotFoundException("Product brand", "provided id", productBrandDto.getProbndsl()));

		oldProductBrand.setBrandName(productBrandDto.getBrandName());

		ProductBrand updatedProductbBrand = this.productBrandRepository.save(oldProductBrand);

		return this.modelMapper.map(updatedProductbBrand, ProductBrandDto.class);
	}

	@Override
	public void deleteProductBrand(String probdid) {
		ProductBrand productBrand = this.productBrandRepository.findById(probdid)
				.orElseThrow(() -> new ResourceNotFoundException("Product brand", "provided id", probdid));

		this.productBrandRepository.delete(productBrand);

	}

	@Override
	public boolean brandNameExist(String brandName) {
		return this.productBrandRepository.existsByBrandName(brandName);
	}

}
