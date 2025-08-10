package com.hana.flower.dto.requestdto;

import lombok.Data;

@Data
public class ProductRequestDto {
	private String productName;
	private String categoryName;
	private String description;
	private InventoryRequestDto inventoryRequestDto;
	
}
