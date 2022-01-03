package com.sahydo.bookingapp.consumer.domain.service;

import com.sahydo.bookingapp.consumer.domain.model.BookingRequest;

public interface IBookingRequestConsumerService {
	public void sendBookingRequest(BookingRequest bookingRequest);
}
