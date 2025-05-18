package com.hana.flower.exception.error;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HanaError 
{       
	    private int statusCode;
	    private String message;
	    private LocalDateTime timeStamp;
	    private String stackTrace;
	    private Map<String, String> fieldErrors;
	    
}
