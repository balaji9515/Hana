package com.hana.flower.dto.requestdto;
import com.hana.flower.enums.UnitOfMeasure;

import lombok.Data;

@Data
public class InventoryRequestDto {

	private Long quantityAvailable;
	private Long quantityReserved;
	private Long totalQuantity;
	private Long quantityThreshold;
	private UnitOfMeasure unitOfMeasure;
	private Long eachUnitPrice;

}
