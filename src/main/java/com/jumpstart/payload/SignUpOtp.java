package com.jumpstart.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignUpOtp {

	@NotBlank
	@NotEmpty(message = "Your OTP can not be empty")
	@Size(min = 6, max = 6, message = "OTP is not valid")
	private String userOtp;

}
