package com.hana.flower.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hana.flower.exception.custom.HanaApplicationException;
import com.hana.flower.exception.error.HanaError;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(HanaApplicationException.class)
	public ResponseEntity<HanaError> handleApplicationException(HanaApplicationException exception,
			WebRequest request) {

		HanaError error = HanaError.builder().timeStamp(LocalDateTime.now()).statusCode(HttpStatus.BAD_REQUEST.value())
				.message(exception.getMessage()).stackTrace(exception.getStackTrace().toString()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

	}
   
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> fieldErrors = new HashMap<>();
		exception.getBindingResult().getFieldErrors()
				.forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

		HanaError error = HanaError.builder().timeStamp(LocalDateTime.now()).statusCode(HttpStatus.BAD_REQUEST.value())
				.message("Validation failed").fieldErrors(fieldErrors).build();

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}