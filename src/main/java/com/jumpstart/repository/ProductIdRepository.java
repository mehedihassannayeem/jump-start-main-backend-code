package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entities.ProductId;

public interface ProductIdRepository extends JpaRepository<ProductId, String> {

	boolean existsByProductId(String proid);

	boolean existsByProductName(String proname);
}
