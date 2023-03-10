package com.jumpstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.payload.ApiResponse;

@RestController
@RequestMapping("/api/v1/users")
public class TestUser {

	@Autowired

	@GetMapping("/home")
	public ResponseEntity<ApiResponse> home() {
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Only user can access this, checked done"),
				HttpStatus.OK);
	}

}
