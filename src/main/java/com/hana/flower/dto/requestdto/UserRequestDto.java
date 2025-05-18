package com.hana.flower.dto.requestdto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
	@NotBlank(message = "username cannot be blank")
	private String userName;

	@NotBlank(message = "password cannot be blank")
	private String password;

	@NotBlank(message = "first name cannot be blank")
	private String firstName;

	@NotBlank(message = "last name cannot be blank")
	private String lastName;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	private String userEmail;
    
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phoneNumber;

}
