package com.jumpstart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.payload.ApiResponse;

@RestController
@RequestMapping("/api/v1/admins")
public class TestAdmin {

	@GetMapping("/home")
	public ResponseEntity<ApiResponse> home() {
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Only admin can access this, checked done"),
				HttpStatus.OK);
	}

}
