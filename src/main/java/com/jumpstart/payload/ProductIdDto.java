package com.jumpstart.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductIdDto {

	private String proidsl;

	@NotEmpty(message = "Product id can not be empty")
	private String productId;

	@NotEmpty(message = "Product name can not be empty")
	private String productName;

}
