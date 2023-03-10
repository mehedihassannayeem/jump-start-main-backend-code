package com.jumpstart.payload;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PasswordRetriveDto {

	private String prid;

	private String retEmail;

	private String retURL;

	private Date createAt;

	private Date validTil;

}
