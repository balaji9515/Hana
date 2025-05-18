package com.hana.flower.model;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_boy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeliveryBoy extends User {

	@Id
	@Column(name = "id", nullable = false)
	private Long id;

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

	@OneToMany(mappedBy = "deliveryBoy", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vehicle> vehicles = new ArrayList<>();

	@OneToMany(mappedBy = "deliveryBoyId", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetails> orders = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_vehicle", nullable = false)
	private Vehicle currentVehicle;
}
