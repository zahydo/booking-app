package com.sahydo.bookingapp.producer.domain.model;

import lombok.Data;

public @Data class BookingRequestWrapper {
	private BookingRequest bookingRequest;
	private BookingRequestLink[] links;
	private BookingRequestLinkSelf _links; 
}
