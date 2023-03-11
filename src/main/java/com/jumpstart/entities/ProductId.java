package com.jumpstart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_ids")
@Setter
@Getter
@NoArgsConstructor
public class ProductId {

	@Id
	@Column(name = "product_id_sl", nullable = false, unique = true)
	private String proidsl;

	@Column(unique = true)
	private String productId;

	@Column(unique = true)
	private String productName;

}
