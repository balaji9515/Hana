package com.hana.flower.dto.responsedto;

import com.hana.flower.dto.requestdto.UserRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponseDto  extends UserRequestDto
{
	private String phoneNumber;	

}
