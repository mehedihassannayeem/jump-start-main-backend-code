package com.jumpstart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	// user querying method
	@PostMapping("/form")
	public ResponseEntity<ApiResponse> newContactedQueries(@Valid @RequestBody ContactUsDto contactUsDto) {
		this.contactUsService.storeUserQueries(contactUsDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true,
				"We received your queries successfully. We will contact you very soon at " + contactUsDto.getEmail()),
				HttpStatus.OK);
	}

	// admin responding method
	@PostMapping("/response")
	public ResponseEntity<ApiResponse> contactFormResponse(@Valid @RequestBody ContactUsDto contactUsDto) {
		this.contactUsService.storeAdminResponse(contactUsDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "You have successfully respondent to "
				+ contactUsDto.getEmail() + " for the querying id of " + contactUsDto.getConid()), HttpStatus.OK);
	}

	// fetching all queries method
	@GetMapping("/get-all-user-queries")
	public ResponseEntity<?> getAllQueries() {
		List<ContactUsDto> allQueries = this.contactUsService.fetchAllQueries();

		if (allQueries.isEmpty() || allQueries == null) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "No quries found"), HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<ContactUsDto>>(allQueries, HttpStatus.FOUND);
	}

	// fetching a query method
	@GetMapping("/get-the-user-query/{conid}")
	public ResponseEntity<ContactUsDto> getAQuery(@PathVariable String conid) {

		ContactUsDto query = this.contactUsService.fetchAQuery(conid);

		return new ResponseEntity<ContactUsDto>(query, HttpStatus.FOUND);
	}

}
