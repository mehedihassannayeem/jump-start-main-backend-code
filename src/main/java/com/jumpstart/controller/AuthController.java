package com.jumpstart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.payload.ApiResponse;
import com.jumpstart.payload.AuthResponse;
import com.jumpstart.payload.LoginRequest;
import com.jumpstart.payload.SignUpRequest;
import com.jumpstart.repository.AccountRepository;
import com.jumpstart.security.TokenProvider;
import com.jumpstart.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	// local user login method
	@PostMapping("/sign-in")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// one check
		if (accountRepository.existsByEmail(loginRequest.getEmail())) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = tokenProvider.createToken(authentication);

			return ResponseEntity.ok(new AuthResponse(token));
		}

		return new ResponseEntity<ApiResponse>(
				new ApiResponse(false, "You are not a registered member, Do registration first !"),
				HttpStatus.NOT_FOUND);

	}

	// local sign up method
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		this.userService.registerLocalUser(signUpRequest);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User registered successfully"),
				HttpStatus.CREATED);
	}

}
