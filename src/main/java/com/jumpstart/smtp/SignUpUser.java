package com.jumpstart.smtp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignUpUser {

	private String signupUserName;
	private String signupUserEmail;
	private String signupUserOTP;

}
