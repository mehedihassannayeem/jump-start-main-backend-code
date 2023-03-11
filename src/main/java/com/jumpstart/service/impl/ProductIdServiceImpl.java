package com.jumpstart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entities.ProductId;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.payload.ProductIdDto;
import com.jumpstart.repository.ProductIdRepository;
import com.jumpstart.service.ProductIdService;

@Service
public class ProductIdServiceImpl implements ProductIdService {

	@Autowired
	private ProductIdRepository productIdRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductIdDto addProductId(ProductIdDto productIdDto) {

		ProductId productId = this.modelMapper.map(productIdDto, ProductId.class);

		// setting the primary key
		productId.setProidsl(UUID.randomUUID().toString());

		ProductId saveProductID = this.productIdRepository.save(productId);

		return this.modelMapper.map(saveProductID, ProductIdDto.class);
	}

	@Override
	public List<ProductIdDto> getProductIds() {
		List<ProductId> productIds = this.productIdRepository.findAll();

		List<ProductIdDto> productIdDtos = productIds.stream()
				.map(productid -> this.modelMapper.map(productid, ProductIdDto.class)).collect(Collectors.toList());
		return productIdDtos;
	}

	@Override
	public ProductIdDto getProductId(String proid) {

		ProductId productId = this.productIdRepository.findById(proid)
				.orElseThrow(() -> new ResourceNotFoundException("Product id", "provided id", proid));

		return this.modelMapper.map(productId, ProductIdDto.class);
	}

	@Override
	public ProductIdDto updateProductId(ProductIdDto productIdDto) {
		ProductId oldProductId = this.productIdRepository.findById(productIdDto.getProidsl()).orElseThrow(
				() -> new ResourceNotFoundException("Product id", "provided id", productIdDto.getProidsl()));

		oldProductId.setProductId(productIdDto.getProductId());
		oldProductId.setProductName(productIdDto.getProductName());

		ProductId updatedProductId = this.productIdRepository.save(oldProductId);

		return this.modelMapper.map(updatedProductId, ProductIdDto.class);
	}

	@Override
	public void deleteProductId(String proid) {
		ProductId productId = this.productIdRepository.findById(proid)
				.orElseThrow(() -> new ResourceNotFoundException("Product id", "provided id", proid));

		this.productIdRepository.delete(productId);

	}

	@Override
	public boolean productIdExist(String proId) {
		return this.productIdRepository.existsByProductId(proId);
	}

	@Override
	public boolean productNameExist(String proName) {
		return this.productIdRepository.existsByProductName(proName);
	}

}
