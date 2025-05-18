package com.hana.flower.model;

import java.time.Instant;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @MapsId 
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Product product;

    @Column(name = "location", columnDefinition = "geography", nullable = false)
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
    private Integer unitOfMeasure;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
