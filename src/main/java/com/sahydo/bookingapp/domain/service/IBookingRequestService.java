package com.sahydo.bookingapp.domain.service;

import java.util.List;

import com.sahydo.bookingapp.domain.model.BookingRequest;
import com.sahydo.bookingapp.presentation.rest.exception.BookingErrorDomainException;
import com.sahydo.bookingapp.presentation.rest.exception.ResourceNotFoundException;

public interface IBookingRequestService {
	public List<BookingRequest> findAll();

	public BookingRequest findById(Long id) throws ResourceNotFoundException;

	public BookingRequest create(BookingRequest BookingRequest) throws BookingErrorDomainException;
}
