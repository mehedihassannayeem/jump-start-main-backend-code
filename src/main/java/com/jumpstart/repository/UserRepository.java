package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
