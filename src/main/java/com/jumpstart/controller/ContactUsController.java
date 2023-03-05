package com.jumpstart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.payload.ApiResponse;
import com.jumpstart.payload.ContactUsDto;
import com.jumpstart.service.ContactUsService;

@RestController
@RequestMapping("/api/v1/contact-us")
public class ContactUsController {

	@Autowired
	private ContactUsService contactUsService;

	@PostMapping("/")
	public ResponseEntity<ApiResponse> newContactedQueries(@Valid @RequestBody ContactUsDto contactUsDto) {
		this.contactUsService.storeUserQueries(contactUsDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true,
				"We received your queries successfully. We will contact you very soon at " + contactUsDto.getEmail()),
				HttpStatus.OK);
	}

}
