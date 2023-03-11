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
	private String spPrice;
	private String regPrice;
	private String model;
	private String series;
	private String partNo;
	private String chipset;
	private String capacity;
	private String intrface;
	private String dimension;
	private String warranty;
	private String quantity;

	private Double rating;
	private String imgUrl;

	private boolean live;
	private boolean stock;

	@OneToOne
	private ProductId pidtbl;

	@OneToOne
	private ProductCategory pcattbl;

	@OneToOne
	private ProductBrand pbantbl;

}
