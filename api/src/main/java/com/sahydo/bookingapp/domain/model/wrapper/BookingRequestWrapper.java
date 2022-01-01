package com.sahydo.bookingapp.domain.model.wrapper;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sahydo.bookingapp.domain.model.BookingRequest;

public class BookingRequestWrapper extends RepresentationModel<BookingRequestWrapper> {
	private final BookingRequest bookingRequest;

	@JsonCreator
	public BookingRequestWrapper(@JsonProperty("bookingRequest") BookingRequest bookingRequest) {
		this.bookingRequest = bookingRequest;
	}

	public BookingRequest getBookingRequest() {
		return bookingRequest;
	}
}
