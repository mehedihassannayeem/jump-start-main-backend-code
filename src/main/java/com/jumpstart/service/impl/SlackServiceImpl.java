package com.jumpstart.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jumpstart.config.AppConstants;
import com.jumpstart.service.SlackService;

@Service
public class SlackServiceImpl implements SlackService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void sendUserQueries(String message) {

		String channelUrl = AppConstants.USER_HOOKS_URL + AppConstants.USER_APP_URL;

		Map<String, String> messageBuilder = new HashMap<>();

		HttpHeaders headers = new HttpHeaders();

		// set header
		headers.setContentType(MediaType.APPLICATION_JSON);
		messageBuilder.put("text", message);
		HttpEntity<Map<String, String>> request = new HttpEntity<>(messageBuilder, headers);
		restTemplate.postForEntity(channelUrl, request, String.class);
	}

	@Override
	public void sendAdminResponse(String message) {

		String channelUrl = AppConstants.ADMIN_HOOKS_URL + AppConstants.ADMIN_APP_URL;

		Map<String, String> messageBuilder = new HashMap<>();

		HttpHeaders headers = new HttpHeaders();

		// set header
		headers.setContentType(MediaType.APPLICATION_JSON);
		messageBuilder.put("text", message);
		HttpEntity<Map<String, String>> request = new HttpEntity<>(messageBuilder, headers);
		restTemplate.postForEntity(channelUrl, request, String.class);
	}

}
