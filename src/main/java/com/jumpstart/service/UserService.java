package com.jumpstart.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.jumpstart.entities.Account;
import com.jumpstart.payload.PasswordChange;
import com.jumpstart.payload.SignUpRequest;
import com.jumpstart.payload.UserDto;
import com.jumpstart.security.UserPrincipal;

public interface UserService {

	// register local user
	void registerLocalUser(SignUpRequest signUpRequest);

	// registering OTP generator
	boolean registrationInterceptor(SignUpRequest signUpRequest);

	// getting currently logged user
	Account getLoggedUser(UserPrincipal userPrincipal);

	// getting currently logged user
	UserDto getOldProfile(HttpServletRequest request);

	// updating user profile
	UserDto updateProfile(UserDto userDto, HttpServletRequest request);

	// uploading user image
	void uploadImage(MultipartFile multipartFile, HttpServletRequest request) throws IOException;

	// forget password OTP generator
	boolean forgetPasswordInterceptor(String name, String email);

	// forget password changing
	boolean forgetPasswordChange(PasswordChange passwordChange);

}
