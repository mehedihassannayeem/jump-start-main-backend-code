package com.jumpstart.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jumpstart.entities.Account;
import com.jumpstart.payload.ApiResponse;
import com.jumpstart.payload.UserDto;
import com.jumpstart.security.CurrentUser;
import com.jumpstart.security.UserPrincipal;
import com.jumpstart.service.FileService;
import com.jumpstart.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	// getting logged in user details
	@GetMapping("/me")
	public Account getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return this.userService.getLoggedUser(userPrincipal);

	}

	// getting profile for update
	@GetMapping("/profile")
	public UserDto getOldProfile(HttpServletRequest request) {
		return this.userService.getOldProfile(request);

	}

	// updating user profile
	@PutMapping("/profile")
	public ResponseEntity<UserDto> updateUserProfile(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {

		return new ResponseEntity<UserDto>(this.userService.updateProfile(userDto, request), HttpStatus.OK);

	}

	// uploading user profile image
	@PostMapping("/profile/upload-profile-image")
	public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile image, HttpServletRequest request)
			throws IOException {

		// insuring the request has a file
		if (image.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Request must have a file"),
					HttpStatus.BAD_REQUEST);
		}

		// uploading the file into server
		this.userService.uploadImage(image, request);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Profile image uploaded successfully"),
				HttpStatus.OK);

	}

	// method to serve user profile image
	@GetMapping(value = "/profile/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
