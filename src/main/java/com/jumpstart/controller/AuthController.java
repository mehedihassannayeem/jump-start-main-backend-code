package com.jumpstart.controller;

import javax.servlet.http.HttpSession;
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
import com.jumpstart.payload.SignUpOtp;
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

	// local user sign up request method

	// local user sign up OTP validating method

	// local sign up method
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		boolean flag = this.userService.registrationInterceptor(signUpRequest);

		if (flag) {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(true, signUpRequest.getName() + "-" + signUpRequest.getEmail()), HttpStatus.OK);
		}

		return new ResponseEntity<ApiResponse>(new ApiResponse(false,
				"Check you email and try again! Unable to send OTP at your provided email address of "
						+ signUpRequest.getEmail()),
				HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/sign-up-otp-verify")
	public ResponseEntity<ApiResponse> registerUserOtp(@Valid @RequestBody SignUpOtp signUpOtp, HttpSession session) {

		final String providedOtp = (String) session.getAttribute("signupOTP");
		final String userOtp = signUpOtp.getUserOtp();
		final SignUpRequest user = (SignUpRequest) session.getAttribute("signupUser");

		if (providedOtp == null) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid Process !!! Start From Beginning"),
					HttpStatus.DESTINATION_LOCKED);
		} else

		if (providedOtp != null && providedOtp.equals(userOtp)) {
			this.userService.registerLocalUser(user);
			session.invalidate();
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User registered successfully"),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid OTP"), HttpStatus.BAD_REQUEST);
		}

	}

}
