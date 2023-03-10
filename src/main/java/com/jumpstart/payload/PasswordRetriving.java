package com.jumpstart.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PasswordRetriving {

	@NotEmpty(message = "Retriving URL can not be empty")
	private String urlPath;

	@NotEmpty(message = "Your create password can not be empty")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Minimum of 8 characters containing one number, small & capital alphabet and special character")
	private String crePassword;

	@NotEmpty(message = "Your confirm password can not be empty")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Minimum of 8 characters containing one number, small & capital alphabet and special character")
	private String conPassword;

}
