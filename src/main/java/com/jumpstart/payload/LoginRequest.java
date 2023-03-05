package com.jumpstart.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequest {

	/**
	 * this class acts as a DTO, from the front-end there is no direct connection
	 * with main entity for login
	 */

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

}
