package com.jumpstart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entities.PasswordRetrive;

public interface PasswordRetriveRepository extends JpaRepository<PasswordRetrive, String> {

	Optional<PasswordRetrive> findByRetURL(String retURL);

}
