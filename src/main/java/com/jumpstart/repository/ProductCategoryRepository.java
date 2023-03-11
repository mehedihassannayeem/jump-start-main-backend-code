package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entities.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

	boolean existsByCatName(String catname);

}
