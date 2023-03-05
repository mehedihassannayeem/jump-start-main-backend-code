package com.jumpstart.security.oauth2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumpstart.entities.Account;
import com.jumpstart.entities.AuthProvider;
import com.jumpstart.exception.OAuth2AuthenticationProcessingException;
import com.jumpstart.repository.AccountRepository;
import com.jumpstart.security.UserPrincipal;
import com.jumpstart.security.oauth2.user.OAuth2UserInfo;
import com.jumpstart.security.oauth2.user.OAuth2UserInfoFactory;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * this method loads the OAuth requesting user
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	/**
	 * this method process the request and send back to the loadUser
	 */
	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		Optional<Account> userOptional = accountRepository.findByEmail(oAuth2UserInfo.getEmail());
		Account account;
		if (userOptional.isPresent()) {
			account = userOptional.get();

			// notifying the user if the user provider is conflicted with one another for
			// the same email
			if (!account.getProvider()
					.equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + account.getProvider() + " account. Please use your "
								+ account.getProvider() + " account to login.");
			}

			// updating the user
			account = updateExistingUser(account, oAuth2UserInfo);
		} else {

			// registering the user
			account = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}

		return UserPrincipal.create(account, oAuth2User.getAttributes());
	}

	/**
	 * this method register the user to the database if the request user is new
	 */
	private Account registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		Account account = new Account();
//		User user = new User();

		account.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		account.setProviderId(oAuth2UserInfo.getId());

		account.setEmail(oAuth2UserInfo.getEmail());

		// setting user
//		user.setName(oAuth2UserInfo.getName());
//		user.setImageUrl(oAuth2UserInfo.getImageUrl());
//		user.setLocalImage(false);
//
//		account.setUtbl(user);
		return accountRepository.save(account);
	}

	/**
	 * this method update the user information if the request user if old
	 */
	private Account updateExistingUser(Account existingUser, OAuth2UserInfo oAuth2UserInfo) {

//		User user = existingUser.getUtbl();
//
//		// profile image will update if the user didn't upload custom image
//		if (!user.isLocalImage()) {
//			user.setImageUrl(oAuth2UserInfo.getImageUrl());
//		}
//
//		existingUser.setUtbl(user);

		return accountRepository.save(existingUser);
	}

}
