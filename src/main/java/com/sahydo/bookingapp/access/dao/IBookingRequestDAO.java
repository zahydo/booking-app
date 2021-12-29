package com.sahydo.bookingapp.access.dao;

import org.springframework.data.repository.CrudRepository;

import com.sahydo.bookingapp.domain.model.BookingRequest;

public interface IBookingRequestDAO extends CrudRepository<BookingRequest, Long>{

}
