package com.sahydo.bookingapp.producer.domain.service;

import java.util.List;

import com.sahydo.bookingapp.producer.domain.model.BookingRequest;
import com.sahydo.bookingapp.producer.domain.model.BookingRequestWrapper;

public interface IBookingRequestService {
	public List<BookingRequestWrapper> findAll();

	public BookingRequestWrapper findById(Long id);

	public BookingRequest create(BookingRequest BookingRequest);
}
