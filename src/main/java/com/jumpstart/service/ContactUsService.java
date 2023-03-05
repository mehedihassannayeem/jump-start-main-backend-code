package com.jumpstart.service;

import com.jumpstart.payload.ContactUsDto;

public interface ContactUsService {

	// user queries store
	void storeUserQueries(ContactUsDto contactUsDto);
}
