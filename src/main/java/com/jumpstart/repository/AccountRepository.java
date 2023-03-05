package com.jumpstart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jumpstart.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	// to get account by user email
	Optional<Account> findByEmail(String email);

	// to check account exist or not
	Boolean existsByEmail(String email);

}
