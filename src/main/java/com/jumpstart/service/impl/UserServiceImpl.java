package com.jumpstart.service.impl;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jumpstart.config.AppConstants;
import com.jumpstart.entities.Account;
import com.jumpstart.entities.AuthProvider;
import com.jumpstart.entities.Role;
import com.jumpstart.entities.User;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.payload.SignUpRequest;
import com.jumpstart.payload.UserDto;
import com.jumpstart.repository.AccountRepository;
import com.jumpstart.repository.RoleRepository;
import com.jumpstart.repository.UserRepository;
import com.jumpstart.security.TokenAuthenticationFilter;
import com.jumpstart.security.TokenProvider;
import com.jumpstart.security.UserPrincipal;
import com.jumpstart.service.FileService;
import com.jumpstart.service.UserService;
import com.jumpstart.smtp.EmailSendingHandlerService;
import com.jumpstart.smtp.SignUpUser;

@Service
public class UserServiceImpl implements UserService {

	/**
	 * Token start
	 */

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Autowired
	private TokenProvider tokenProvider;

	/**
	 * Token finish
	 */

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Override
	public Account getLoggedUser(UserPrincipal userPrincipal) {
		return accountRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

	}

	@Override
	public UserDto getOldProfile(HttpServletRequest request) {

		String token = this.tokenAuthenticationFilter.getJwtFromRequest(request);
		Long id = this.tokenProvider.getUserIdFromToken(token);
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		User user = account.getUtbl();

		return this.modelMapper.map(user, UserDto.class);

	}

	@Override
	public boolean registrationInterceptor(SignUpRequest signUpRequest) {

		boolean confirmedUser = false;
		HttpSession session = httpServletRequest.getSession();

		// calling the email control handler
		EmailSendingHandlerService eshs = new EmailSendingHandlerService();
		String signupOTP = eshs.getSignUpOtp();

		SignUpUser regUser = new SignUpUser(signUpRequest.getName(), signUpRequest.getEmail(), signupOTP);

		confirmedUser = eshs.signupEmailSend(regUser, this.fileService.emailLogoPath());
//		confirmedUser = true;

		if (confirmedUser) {
			session.setAttribute("signupOTP", signupOTP);
			session.setAttribute("signupUser", signUpRequest);
		}

		System.out.println("\r\r\r------------------ OTP = " + signupOTP + "\r\r\r");

		return confirmedUser;
	}

	@Override
	public void registerLocalUser(SignUpRequest signUpRequest) {
		Account account = new Account();

		account.setEmail(signUpRequest.getEmail());
		account.setPassword(signUpRequest.getPassword());
		account.setProvider(AuthProvider.local);

		account.setPassword(passwordEncoder.encode(account.getPassword()));

		// setting the user
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setLocalImage(true);

		// getting role
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		// setting role
		user.getRoles().add(role);

		account.setUtbl(user);

		accountRepository.save(account);

	}

	@Override
	public UserDto updateProfile(UserDto userDto, HttpServletRequest request) {

		String token = this.tokenAuthenticationFilter.getJwtFromRequest(request);
		Long id = this.tokenProvider.getUserIdFromToken(token);
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		User oldUserData = account.getUtbl();

		User userUpdateData = this.modelMapper.map(userDto, User.class);

		oldUserData.setName(userUpdateData.getName());
		oldUserData.setDob(userUpdateData.getDob());
		oldUserData.setGender(userUpdateData.getGender());
		oldUserData.setPhnum(userUpdateData.getPhnum());
		oldUserData.setNid(userUpdateData.getNid());
		oldUserData.setStreet(userUpdateData.getStreet());
		oldUserData.setState(userUpdateData.getState());
		oldUserData.setCity(userUpdateData.getCity());
		oldUserData.setCountry(userUpdateData.getCountry());

		// getting user all roles and setting from old one
		Set<Role> roles = oldUserData.getRoles();
		for (Role role : roles) {
			userUpdateData.getRoles().add(role);
		}

		User userUpdatedData = this.userRepository.save(oldUserData);

		return this.modelMapper.map(userUpdatedData, UserDto.class);
	}

	@Override
	public void uploadImage(MultipartFile multipartFile, HttpServletRequest request) throws IOException {

		String token = this.tokenAuthenticationFilter.getJwtFromRequest(request);
		Long id = this.tokenProvider.getUserIdFromToken(token);
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		User oldUserData = account.getUtbl();

		// deleting old image
		if (oldUserData.getImageUrl() != null && oldUserData.isLocalImage()) {
			this.fileService.deleteFile(oldUserData.getImageUrl());
		}

		// getting new file name
		String uploadedImage = fileService.uploadImage(multipartFile);

		// setting image name
		oldUserData.setImageUrl(uploadedImage);
		oldUserData.setLocalImage(true);

		// updating user
		this.userRepository.save(oldUserData);

	}

}
