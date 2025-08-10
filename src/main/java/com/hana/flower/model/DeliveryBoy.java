package com.hana.flower.model;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "delivery_boy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class DeliveryBoy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "licence_no", nullable = false)
	private String licenceNo;

	@Column(name = "aadhar_no", nullable = false)
	private String aadharNo;

	@Column(name = "is_working", nullable = false)
	private boolean isWorking;

	@Column(name = "assigned_loc", nullable = false, columnDefinition = "geography")
	private Point assignedLoc;

	@Column(name = "total_deliveries", nullable = false)
	private Long totalDeliveries;

	@Column(name = "total_km", nullable = false)
	private Long totalKm;

	@Builder.Default
	@OneToMany(mappedBy = "deliveryBoy", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vehicle> vehicles = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "deliveryBoy", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetails> orders = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_vehicle", nullable = false)
	private Vehicle currentVehicle;

	@Column(name = "is_available", nullable = false)
	private boolean isAvailable;
}
