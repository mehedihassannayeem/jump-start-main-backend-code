package com.jumpstart.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.payload.ApiResponse;

@RestController
@RequestMapping("/api/v1/users")
public class TestUser {

//	@Autowired

	@GetMapping("/home")
	public ResponseEntity<ApiResponse> home() {

		System.out.println(UUID.randomUUID());

		ArrayList<String> al = new ArrayList<>();

		String htmlinit = "<html>";

		String htmlend = "</html>";

		String items = "";

		al.add("Apple");
		al.add("Orange");
		al.add("Mango");

		String htmlbefore = htmlinit + items + htmlend;

		System.out.println(htmlbefore);

		for (String item : al) {
			items += "<div> item name = " + item + " </div>";

		}

		String htmlafter = htmlinit + items + htmlend;

		System.out.println(htmlafter);

//		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "SIZE = " + al.size()), HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, htmlafter), HttpStatus.OK);
	}

}
