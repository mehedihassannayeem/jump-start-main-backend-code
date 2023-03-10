package com.jumpstart.smtp;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResetProfilePassword {

	private String resetUserName;
	private String resetUserEmail;
	private String resetUserOTP;
	private Date resetUserOTPValidTime;

}
