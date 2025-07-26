package com.hana.flower.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hana.flower.model.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
	@Query("SELECT u from User u where u.phoneNumber=:phoneNumber")
	Optional<User> getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
}

