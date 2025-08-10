package com.hana.flower.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hana.flower.dto.requestdto.UserRequestDto;
import com.hana.flower.dto.responsedto.UserResponseDto;
import com.hana.flower.model.User;
import com.hana.flower.service.UserService;
import com.hana.flower.util.DtoConverter;
import com.hana.flower.wrapper.ServiceResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private final UserService userService;

	@Autowired
	private final ModelMapper modelMapper;
	
	@Autowired
	private DtoConverter dtoConverter;

	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		ServiceResponse<List<User>> serviceResponse = userService.getAllUsers();
		List<UserResponseDto> response = serviceResponse.getData().stream()
				.map(user -> modelMapper.map(user, UserResponseDto.class)).toList();
		ServiceResponse<List<UserResponseDto>> dtoResponse = ServiceResponse.of("List of Users", response,
				serviceResponse.getHttpStatus());
		return new ResponseEntity<>(dtoResponse, dtoResponse.getHttpStatus());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		
		ServiceResponse<User>serviceResponse=userService.getUserById(id);
		ServiceResponse<UserResponseDto>responseDto=dtoConverter.entityToDto(serviceResponse, UserResponseDto.class);
		return new ResponseEntity<>(responseDto,responseDto.getHttpStatus());
		
	}

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDto requestDto) {
		ServiceResponse<User>serviceResponse=userService.createUser(requestDto);
		ServiceResponse<UserResponseDto>responseDto=dtoConverter.entityToDto(serviceResponse, UserResponseDto.class);
		return new ResponseEntity<>(responseDto,responseDto.getHttpStatus());

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id,
			@RequestBody @Valid UserRequestDto requestDto) {		
		ServiceResponse<User>serviceResponse=userService.updateUser(id,requestDto);
		ServiceResponse<UserResponseDto>responseDto=dtoConverter.entityToDto(serviceResponse, UserResponseDto.class);
		return new ResponseEntity<>(responseDto,responseDto.getHttpStatus());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		ServiceResponse<User>serviceResponse=userService.deleteUser(id);
		ServiceResponse<UserResponseDto>responseDto=dtoConverter.entityToDto(serviceResponse, UserResponseDto.class);
		return new ResponseEntity<>(responseDto,responseDto.getHttpStatus());
	}
}
