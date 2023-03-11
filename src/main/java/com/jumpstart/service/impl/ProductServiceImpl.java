package com.jumpstart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entities.Product;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.payload.ProductDto;
import com.jumpstart.repository.ProductRepository;
import com.jumpstart.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		Product product = this.modelMapper.map(productDto, Product.class);
		product.setProsl(UUID.randomUUID().toString());

		// category adding
		// brand adding
		// product id adding

		Product savedProduct = this.productRepository.save(product);

		return this.modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto getProduct(String proid) {
		Product product = this.productRepository.findById(proid)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "provided id", proid));

		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public List<ProductDto> getProducts() {
		List<Product> products = this.productRepository.findAll();

		List<ProductDto> productDtos = products.stream().map(product -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
		return productDtos;
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto) {
		Product oldProduct = this.productRepository.findById(productDto.getProsl())
				.orElseThrow(() -> new ResourceNotFoundException("Product", "provided id", productDto.getProsl()));

//		oldProductId.setProductId(productIdDto.getProductId());
		oldProduct.setProName(productDto.getProName());

		// category adding
		// brand adding
		// product id adding

		Product updatedProduct = this.productRepository.save(oldProduct);

		return this.modelMapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void deleteProduct(String proid) {
		Product product = this.productRepository.findById(proid)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "provided id", proid));

		this.productRepository.delete(product);
	}

	@Override
	public List<ProductDto> getCategoryProducts(String catId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDto> getBrandProducts(String brandId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDto> getSearchedProducts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
