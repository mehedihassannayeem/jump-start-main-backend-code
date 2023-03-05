package com.jumpstart.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long aid;

	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@JsonIgnore
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private String providerId;

	@OneToOne(cascade = CascadeType.ALL)
	private User utbl;

}
