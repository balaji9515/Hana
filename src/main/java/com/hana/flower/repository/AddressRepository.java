package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>
{
	
}

