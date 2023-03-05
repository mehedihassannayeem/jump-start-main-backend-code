package com.jumpstart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumpstart.entities.Account;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;

	/**
	 * method for loading the user or else throwing exception if user not exist for
	 * provided email
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account user = accountRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

		return UserPrincipal.create(user);
	}

	/**
	 * method for loading the user or else throwing exception if user not exist for
	 * provided id
	 */
	@Transactional
	public UserDetails loadUserById(Long id) {
		Account user = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		return UserPrincipal.create(user);
	}
}