package com.jumpstart.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	// file uploading method
	String uploadImage(MultipartFile file) throws IOException;

	// file serving method
	InputStream getResource(String fileName) throws FileNotFoundException;

	// file deleting method
	void deleteFile(String filename) throws IOException;

	// email image path
	String emailLogoPath();
}
