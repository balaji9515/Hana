package com.hana.flower.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hana.flower.dto.requestdto.UserRequestDto;
import com.hana.flower.enums.UserType;
import com.hana.flower.exception.custom.HanaApplicationException;
import com.hana.flower.model.Cart;
import com.hana.flower.model.User;
import com.hana.flower.repository.UserRepository;
import com.hana.flower.wrapper.ServiceResponse;

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

	public ServiceResponse<List<User>> getAllUsers() {

		return ServiceResponse.of("List of Users", userRepository.findAll(), HttpStatus.OK);

	}

	public ServiceResponse<User> getUserById(Long id) {

		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty()) {
			return ServiceResponse.of("user Not Found", new User(), HttpStatus.NOT_FOUND);
		} else {
			return ServiceResponse.of("User found", user.get(), HttpStatus.OK);
		}

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

	public ServiceResponse<User> createUser(UserRequestDto userDetails) {

		User user = getUserByPhoneNumber(userDetails.getPhoneNumber());

		if (Objects.isNull(user)) {

			User createdUser = User.builder().firstName(userDetails.getFirstName()).lastName(userDetails.getLastName())
					.password(userDetails.getPassword()).userEmail(userDetails.getUserEmail())
					.phoneNumber(userDetails.getPhoneNumber()).userName(userDetails.getUserName())
					.userType(UserType.CUSTOMER).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

			Cart cart = Cart.builder().user(createdUser).totalPrice(0L).createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now()).build();
			createdUser.setCart(cart);
			User saved = userRepository.save(createdUser);
			return ServiceResponse.of("user created", saved, HttpStatus.CREATED);
		}

		else {
			return ServiceResponse.of("User Already Existed", user, HttpStatus.CONFLICT);
		}

	}

	public ServiceResponse<User> updateUser(Long id, UserRequestDto userDetails) {
		Optional<User> optonalUser = userRepository.findById(id);
		User user = optonalUser.get();
		if (Objects.isNull(user)) {
			return ServiceResponse.of("User Not Found", new User(), HttpStatus.NOT_FOUND);
		} else {
			modelMapper.map(userDetails, user);
			User updated = userRepository.save(user);
			return ServiceResponse.of("User Updated Successfully", updated, HttpStatus.OK);
		}

	}

	public ServiceResponse<User> deleteUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (Objects.isNull(user)) {
			return ServiceResponse.of("User Not Found", new User(), HttpStatus.NOT_FOUND);
		} else {
			userRepository.delete(user.get());
			return ServiceResponse.of("user deleted successfully", user.get(), HttpStatus.OK);
		}

	}
}
