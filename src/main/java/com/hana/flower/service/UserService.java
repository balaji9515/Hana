package com.hana.flower.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hana.flower.dto.requestdto.UserRequestDto;
import com.hana.flower.dto.responsedto.UserResponseDto;
import com.hana.flower.enums.UserType;
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

	public ResponseEntity<?> getUserById(Long id) {
		User user = getUser(id);
		if(null==user) return new ResponseEntity<>("user not found",HttpStatus.OK);
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

	public User getUserByPhoneNumber(String phoneNumber) {
		User user = null;
		try {
			user = userRepository.getUserByPhoneNumber(phoneNumber)
					.orElseThrow(() -> new HanaApplicationException("User not existed"));
		} catch (HanaApplicationException e) {
			log.info(e.getMessage());
		}

		return user;
	}

	public ResponseEntity<UserResponseDto> createUser(UserRequestDto userDetails) {

		User user = getUserByPhoneNumber(userDetails.getPhoneNumber());

		if (null == user) {

			User createdUser = User.builder().firstName(userDetails.getFirstName()).lastName(userDetails.getLastName())
					.password(userDetails.getPassword()).userEmail(userDetails.getUserEmail())
					.phoneNumber(userDetails.getPhoneNumber()).userName(userDetails.getUserName())
					.userType(UserType.CUSTOMER).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
			User saved = userRepository.save(createdUser);
			return new ResponseEntity<>(modelMapper.map(saved, UserResponseDto.class),HttpStatus.CREATED);
		}

		else {
			return new ResponseEntity<>(modelMapper.map(user, UserResponseDto.class),HttpStatus.CONFLICT);
		}

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
