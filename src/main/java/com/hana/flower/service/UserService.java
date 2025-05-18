package com.hana.flower.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hana.flower.repository.UserRepository;

@Service
public class UserService {
     
	@Autowired
    private final UserRepository userRepository;
	


}
