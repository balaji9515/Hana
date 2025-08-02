package com.hana.flower.wrapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ch.qos.logback.core.model.Model;
import lombok.Data;

@Data
public class ServiceResponse<T> {
    private String message;
    private T data;
    private HttpStatus httpStatus;

    private ServiceResponse(String message, T data, HttpStatus httpStatus) {
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public static <T> ServiceResponse<T> of(String message, T data, HttpStatus httpStatus) {
        return new ServiceResponse<>(message, data, httpStatus);
    }
    
}

