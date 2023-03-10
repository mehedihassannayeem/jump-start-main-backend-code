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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.entities.Account;
import com.jumpstart.payload.ApiResponse;
import com.jumpstart.payload.AuthResponse;
import com.jumpstart.payload.LoginRequest;
import com.jumpstart.payload.PasswordChange;
import com.jumpstart.payload.SignUpRequest;
import com.jumpstart.payload.UserOtpDto;
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

	// sign up email verification method
	@PostMapping("/sign-up-otp-verify")
	public ResponseEntity<ApiResponse> registerUserOtp(@Valid @RequestBody UserOtpDto userOtpDto,
			HttpSession signUpHS) {

		final String providedOtp = (String) signUpHS.getAttribute("signupOTP");
		final String userOtp = userOtpDto.getUserOtp();
		final SignUpRequest user = (SignUpRequest) signUpHS.getAttribute("signupUser");

		if (providedOtp == null) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid Process !!! Start From Beginning"),
					HttpStatus.DESTINATION_LOCKED);
		} else if (providedOtp != null && providedOtp.equals(userOtp)) {
			this.userService.registerLocalUser(user);
			signUpHS.invalidate();
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User registered successfully"),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid OTP"), HttpStatus.BAD_REQUEST);
		}

	}

	// forget password module find user method
	@PostMapping("/forget-user-profile")
	public ResponseEntity<ApiResponse> forgetUserProfile(@RequestParam String cre) {

		if (accountRepository.existsByEmail(cre)) {
			Account account = this.accountRepository.findByEmail(cre).get();

			boolean flag = this.userService.forgetPasswordInterceptor(account.getUtbl().getName(), account.getEmail());

			if (flag) {
				return new ResponseEntity<ApiResponse>(
						new ApiResponse(true, account.getUtbl().getName() + "-" + account.getEmail()), HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiResponse>(
						new ApiResponse(false,
								"Unable to send OTP at your provided email address ! Try again after some times."),
						HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false, "You are not a registered member. Considering registration first !"),
					HttpStatus.NOT_ACCEPTABLE);
		}

	}

	// forget password module otp verification method
	@PostMapping("/forget-user-profile-otp-verify")
	public ResponseEntity<ApiResponse> forgetUserProfileOtp(@Valid @RequestBody UserOtpDto userOtpDto,
			HttpSession resetPassHS) {

		final String providedOtp = (String) resetPassHS.getAttribute("resPassOTP");
		final String userOtp = userOtpDto.getUserOtp();
		final String userEmail = (String) resetPassHS.getAttribute("resPassUser");

		if (providedOtp == null) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid Process !!! Start From Beginning"),
					HttpStatus.DESTINATION_LOCKED);
		} else if (providedOtp != null && providedOtp.equals(userOtp)) {
			resetPassHS.invalidate();
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, userEmail), HttpStatus.OK);
		} else {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid OTP"), HttpStatus.BAD_REQUEST);
		}
	}

	// forget password module password changing method and reverting method
	@PostMapping("/forget-user-profile-password-verify")
	public ResponseEntity<ApiResponse> forgetUserPasswordChange(@Valid @RequestBody PasswordChange passwordChange) {

		if (passwordChange.getCrePassword().equals(passwordChange.getConPassword())) {
			if (accountRepository.existsByEmail(passwordChange.getForEmail())) {

				this.userService.forgetPasswordChange(passwordChange);

				return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Password changed successfully"),
						HttpStatus.OK);

			} else {
				return new ResponseEntity<ApiResponse>(
						new ApiResponse(false, "Unable to find your profile! Please try again later."),
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<ApiResponse>(
					new ApiResponse(false, "Created and Confirmed passwords are mismatched !"),
					HttpStatus.NOT_ACCEPTABLE);
		}

	}

}
