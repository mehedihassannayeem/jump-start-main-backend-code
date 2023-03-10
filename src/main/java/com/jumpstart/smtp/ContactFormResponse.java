package com.jumpstart.smtp;

public class ContactFormResponse {

	private String user_name;
	private String user_email;
	private String user_query;
	private String user_res_id;
	private String response;
	private String respondent;

	public ContactFormResponse(String user_name, String user_email, String user_query, String user_res_id,
			String response, String respondent) {
		super();
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_query = user_query;
		this.user_res_id = user_res_id;
		this.response = response;
		this.respondent = respondent;
	}

	public String getUser_res_id() {
		return user_res_id;
	}

	public void setUser_res_id(String user_res_id) {
		this.user_res_id = user_res_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_query() {
		return user_query;
	}

	public void setUser_query(String user_query) {
		this.user_query = user_query;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getRespondent() {
		return respondent;
	}

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

}
