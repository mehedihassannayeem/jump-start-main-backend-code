package com.jumpstart.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "password_retriving_credentails")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRetrive {

	@Id
	@Column(name = "retrive_sl_no", unique = true)
	private String prid;

	@Column(unique = true)
	private String retURL;

	private String retEmail;

	private Date createAt;

	private Date validTil;

}
