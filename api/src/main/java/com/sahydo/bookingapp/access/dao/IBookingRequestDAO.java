package com.sahydo.bookingapp.access.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sahydo.bookingapp.domain.model.BookingRequest;

public interface IBookingRequestDAO extends PagingAndSortingRepository<BookingRequest, Long>{

}
