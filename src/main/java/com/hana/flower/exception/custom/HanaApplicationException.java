package com.hana.flower.exception.custom;

public class HanaApplicationException extends Exception
{
	
	private static final long serialVersionUID = 7809053472112110777L;

	public HanaApplicationException(String message)
	{
		super(message);
	}
    
}
