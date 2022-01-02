package com.sahydo.bookingapp.producer.domain.service;

import java.util.List;

import com.sahydo.bookingapp.producer.domain.model.BookingRequest;

public interface IBookingRequestService {
	public List<BookingRequest> findAll();

	public BookingRequest findById(Long id);

	public BookingRequest create(BookingRequest BookingRequest);
}
