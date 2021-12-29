package com.sahydo.bookingapp.presentation.rest.exception;

import lombok.Data;

public @Data class BookingRequestError {
	private final EnumErrorCode code;
	private final String field;
	private final String message;
}
