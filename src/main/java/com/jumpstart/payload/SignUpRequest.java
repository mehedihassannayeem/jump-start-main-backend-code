package com.jumpstart.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignUpRequest {

	/**
	 * this class acts as a DTO, from the front-end there is no direct connection
	 * with main entity for registering
	 */

	@NotBlank
	@NotEmpty(message = "Your name can not be empty")
	private String name;

	@NotBlank
	@NotEmpty(message = "Your email can not be empty")
	@Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "Enter a valid email address")
	private String email;

	@NotBlank
	@NotEmpty(message = "Password can not be empty")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Minimum of 8 characters containing one number, small & capital alphabet and special character")
	private String password;

}
