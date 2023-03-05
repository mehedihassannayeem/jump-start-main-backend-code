package com.jumpstart.security.oauth2.user;

import java.util.Map;

import com.jumpstart.entities.AuthProvider;
import com.jumpstart.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

	/**
	 * this class check the auth provider and according pass the values to generate
	 * the user object for registering. if the provider is not in our system will
	 * through the exception also
	 */

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException(
					"Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}
