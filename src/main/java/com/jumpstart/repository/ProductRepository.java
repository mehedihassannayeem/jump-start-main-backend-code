package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	// searching
}
