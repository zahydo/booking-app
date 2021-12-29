package com.sahydo.bookingapp.presentation.rest.exception;

import java.util.List;

public class ErrorsPayload {
	public final List<BookingRequestError> errors;
	
	public ErrorsPayload(List<BookingRequestError> applicationErrors) {
		this.errors = applicationErrors;
	}
}
