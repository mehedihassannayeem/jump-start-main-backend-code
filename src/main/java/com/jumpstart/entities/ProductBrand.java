package com.jumpstart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_brand_names")
@Setter
@Getter
@NoArgsConstructor
public class ProductBrand {

	@Id
	@Column(name = "product_brand_sl", nullable = false, unique = true)
	private String probndsl;

	@Column(unique = true)
	private String brandName;

}
