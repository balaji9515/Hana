package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
