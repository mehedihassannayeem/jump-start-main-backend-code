package com.jumpstart.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PasswordChange {

	@NotEmpty(message = "Your email can not be empty")
	@Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "Enter a valid email address")
	private String forEmail;

	@NotEmpty(message = "Your create password can not be empty")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Minimum of 8 characters containing one number, small & capital alphabet and special character")
	private String crePassword;

	@NotEmpty(message = "Your confirm password can not be empty")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Minimum of 8 characters containing one number, small & capital alphabet and special character")
	private String conPassword;

}
