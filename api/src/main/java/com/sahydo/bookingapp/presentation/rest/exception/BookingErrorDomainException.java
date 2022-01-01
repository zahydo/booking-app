package com.sahydo.bookingapp.presentation.rest.exception;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingErrorDomainException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final List<BookingRequestError> errors;
	
}
