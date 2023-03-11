package com.jumpstart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_categories")
@Setter
@Getter
@NoArgsConstructor
public class ProductCategory {

	@Id
	@Column(name = "product_category_sl", nullable = false, unique = true)
	private String procatsl;

	@Column(unique = true)
	private String catName;

	private String catIcon;
}
