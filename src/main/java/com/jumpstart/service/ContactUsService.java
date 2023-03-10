package com.jumpstart.service;

import java.util.List;

import com.jumpstart.payload.ContactUsDto;

public interface ContactUsService {

	// user queries store
	void storeUserQueries(ContactUsDto contactUsDto);

	// admin response store
	void storeAdminResponse(ContactUsDto contactUsDto);

	// user queries store
	List<ContactUsDto> fetchAllQueries();

	// user queries store
	ContactUsDto fetchAQuery(String conId);
}
