package com.hana.flower.model;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;

import com.hana.flower.enums.UnitOfMeasure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "inventory_id", nullable = false)
    private Long inventoryId;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "location", columnDefinition = "geography", nullable = true)
    private Point location; 

    @Column(name = "quantity_available", nullable = false)
    private Long quantityAvailable;

    @Column(name = "quantity_reserved", nullable = false)
    private Long quantityReserved;

    @Column(name = "total_quantity", nullable = false)
    private Long totalQuantity;

    @Column(name = "quantity_threshold", nullable = false)
    private Long quantityThreshold;

    @Column(name = "unit_of_measure", nullable = false)
    private UnitOfMeasure unitOfMeasure;
    
    @Column(name= "price", nullable=false)
    private Long price;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
