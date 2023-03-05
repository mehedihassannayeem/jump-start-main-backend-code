package com.jumpstart.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ContactUsDto {

	private int conid;

	@NotEmpty(message = "Your Name can not be empty")
	private String name;

	@NotEmpty(message = "Your mail address can not be empty")
	private String email;

	@NotEmpty(message = "Your queries can not be empty")
	@Size(min = 15, max = 50000, message = "Your queries must be between 50 to 50000 characters in length")
	private String queries;

	private String response;

	private String res_at;

	private String respondent;

}
