package com.hana.flower.dto.responsedto;

import com.hana.flower.enums.UnitOfMeasure;

import lombok.Data;

@Data
public class InventoryResponseDto {
	private Long quantityAvailable;
	private Long quantityReserved;
	private Long totalQuantity;
	private Long quantityThreshold;
	private UnitOfMeasure unitOfMeasure;
	private Long eachUnitPrice;

}
