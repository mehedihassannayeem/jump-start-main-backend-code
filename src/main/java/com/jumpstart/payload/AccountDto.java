package com.jumpstart.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jumpstart.entities.AuthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountDto {

	private Long aid;

	private String email;

	@JsonIgnore
	private String password;

	private AuthProvider provider;

	private String providerId;

	private UserDto utbl;

}
