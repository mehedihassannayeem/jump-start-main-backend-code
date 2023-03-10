package com.jumpstart.service;

public interface SlackService {

	// send user queries to slack
	void sendUserQueries(String message);

	// send admin response to slack
	void sendAdminResponse(String message);

}
