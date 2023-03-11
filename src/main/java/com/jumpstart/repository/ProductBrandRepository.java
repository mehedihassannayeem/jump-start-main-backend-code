package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entities.ProductBrand;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, String> {

	boolean existsByBrandName(String brandname);

}
