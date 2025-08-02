package com.hana.flower.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hana.flower.wrapper.ServiceResponse;

@Component
public class DtoConverter {

	@Autowired
	private ModelMapper modelMapper;

	public <D, T> ServiceResponse<D> entityToDto(ServiceResponse<T> entityResponse, Class<D> dtoClass) {
		D dto = modelMapper.map(entityResponse.getData(), dtoClass);
		return  ServiceResponse.of(entityResponse.getMessage(), dto, entityResponse.getHttpStatus());
	}
}
