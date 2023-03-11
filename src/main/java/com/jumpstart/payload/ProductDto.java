package com.jumpstart.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductDto {

	// validations
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

	private ProductIdDto pidtbl;

	private ProductCategoryDto pcattbl;

	private ProductBrandDto pbantbl;

}
