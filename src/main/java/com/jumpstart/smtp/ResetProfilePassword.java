package com.jumpstart.smtp;

public class ResetProfilePassword {

	private String resetUserName;
	private String resetUserEmail;
	private String resetUserOTP;

	public ResetProfilePassword(String resetUserName, String resetUserEmail, String resetUserOTP) {
		super();
		this.resetUserName = resetUserName;
		this.resetUserEmail = resetUserEmail;
		this.resetUserOTP = resetUserOTP;
	}

	public String getResetUserName() {
		return resetUserName;
	}

	public void setResetUserName(String resetUserName) {
		this.resetUserName = resetUserName;
	}

	public String getResetUserEmail() {
		return resetUserEmail;
	}

	public void setResetUserEmail(String resetUserEmail) {
		this.resetUserEmail = resetUserEmail;
	}

	public String getResetUserOTP() {
		return resetUserOTP;
	}

	public void setResetUserOTP(String resetUserOTP) {
		this.resetUserOTP = resetUserOTP;
	}

}
