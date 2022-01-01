package com.sahydo.bookingapp.presentation.rest.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingRequestError {
	public final EnumErrorCode code;
	public final String field;
	public final String message;
}
