package com.hana.flower.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hana.flower.dto.requestdto.UserRequestDto;
import com.hana.flower.dto.responsedto.UserResponseDto;
import com.hana.flower.exception.custom.HanaApplicationException;
import com.hana.flower.model.User;
import com.hana.flower.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final ModelMapper modelMapper;

	public List<UserResponseDto> getAllUsers() {
		return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserResponseDto.class))
				.collect(Collectors.toList());
 
	}

	public ResponseEntity<UserResponseDto> getUserById(Long id) {
		User user = getUser(id);
		return new ResponseEntity<UserResponseDto>(modelMapper.map(user, UserResponseDto.class), HttpStatus.OK);
	}

	public User getUser(Long id) {
		try {
			return userRepository.findById(id).orElseThrow(() -> new HanaApplicationException("User not found"));
		} catch (HanaApplicationException e) {
			log.info(e.getMessage());
		}
		return null;
	}

	public ResponseEntity<UserResponseDto> createUser(UserRequestDto userDetails) {
		User user = modelMapper.map(userDetails, User.class);
		User saved = userRepository.save(user);
		return new ResponseEntity<UserResponseDto>(modelMapper.map(saved, UserResponseDto.class), HttpStatus.OK);

	}

	public ResponseEntity<UserResponseDto> updateUser(Long id, UserRequestDto userDetails) {
		User existing = getUser(id);
		modelMapper.map(userDetails, existing); // update fields
		User updated = userRepository.save(existing);
		return new ResponseEntity<UserResponseDto>(modelMapper.map(updated, UserResponseDto.class), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteUser(Long id) {
		User product = getUser(id);
		userRepository.delete(product);
		return new ResponseEntity<String>("User deleted succesfully", HttpStatus.OK);

	}
}
