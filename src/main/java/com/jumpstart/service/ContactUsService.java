package com.jumpstart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jumpstart.payload.ContactUsDto;

public interface ContactUsService {

	// user queries store
	void storeUserQueries(ContactUsDto contactUsDto);

	// admin response store
	void storeAdminResponse(ContactUsDto contactUsDto, HttpServletRequest request);

	// user queries store
	List<ContactUsDto> fetchAllQueries();

	// user queries store
	ContactUsDto fetchAQuery(String conId);
}
