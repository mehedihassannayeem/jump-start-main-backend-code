package com.jumpstart.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

	private Long uid;

	@NotEmpty(message = "Your name can not be empty")
	private String name;

	private boolean localImage;
	private String imageUrl;

	private String dob;
	private String gender;
	private String phnum;
	private String nid;

	private String street;
	private String state;
	private String city;
	private String country;

}
