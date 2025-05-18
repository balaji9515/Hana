package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
	
}

