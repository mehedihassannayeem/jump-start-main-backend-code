package com.jumpstart.smtp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ContactFormResponse {

	private String userName;
	private String userEmail;
	private String userQuery;
	private String userResId;
	private String response;
	private String respondent;

}
