package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
