package com.jumpstart.smtp;

public class InviteUsers {

	private String email_1;
	private String email_2;
	private String email_3;
	private String email_4;
	private String email_5;

	public InviteUsers(String email_1, String email_2, String email_3, String email_4, String email_5) {
		super();
		this.email_1 = email_1;
		this.email_2 = email_2;
		this.email_3 = email_3;
		this.email_4 = email_4;
		this.email_5 = email_5;
	}

	public String getEmail_1() {
		return email_1;
	}

	public void setEmail_1(String email_1) {
		this.email_1 = email_1;
	}

	public String getEmail_2() {
		return email_2;
	}

	public void setEmail_2(String email_2) {
		this.email_2 = email_2;
	}

	public String getEmail_3() {
		return email_3;
	}

	public void setEmail_3(String email_3) {
		this.email_3 = email_3;
	}

	public String getEmail_4() {
		return email_4;
	}

	public void setEmail_4(String email_4) {
		this.email_4 = email_4;
	}

	public String getEmail_5() {
		return email_5;
	}

	public void setEmail_5(String email_5) {
		this.email_5 = email_5;
	}

}
