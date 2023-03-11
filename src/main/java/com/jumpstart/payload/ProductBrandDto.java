package com.jumpstart.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductBrandDto {

	private String probndsl;

	@NotEmpty(message = "Brand name can not be empty")
	private String brandName;

}
