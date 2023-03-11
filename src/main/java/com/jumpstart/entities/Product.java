package com.jumpstart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
public class Product {

	@Id
	@Column(name = "product_sl", nullable = false, unique = true)
	private String prosl;

	private String proName;

	@OneToOne
	private ProductId pidtbl;

	@OneToOne
	private ProductCategory pcattbl;

}
