package com.sahydo.bookingapp.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sahydo.bookingapp.access.dao.IBookingRequestDAO;
import com.sahydo.bookingapp.domain.model.BookingRequest;
import com.sahydo.bookingapp.presentation.rest.exception.BookingErrorDomainException;
import com.sahydo.bookingapp.presentation.rest.exception.BookingRequestError;
import com.sahydo.bookingapp.presentation.rest.exception.ResourceNotFoundException;

@Service
public class BookingRequestImplService implements IBookingRequestService {

	@Autowired
	private IBookingRequestDAO bookingRequestDAO;

	@Override
	@Transactional(readOnly = true) // Para que esté sincronizada con la bd
	public List<BookingRequest> findAll() {
		return (List<BookingRequest>) bookingRequestDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true) // Para que esté sincronizada con la bd
	public BookingRequest findById(Long id) throws ResourceNotFoundException {
		BookingRequest bookingRequest = bookingRequestDAO.findById(id).orElse(null);
		if (bookingRequest == null) {
			throw new ResourceNotFoundException();
		}
		return bookingRequest;
	}

	@Override
	@Transactional
	public BookingRequest create(BookingRequest bookingRequest) throws BookingErrorDomainException {
		List<BookingRequestError> errors = validateBookingRequest(bookingRequest);
		if (!errors.isEmpty()) {
			throw new BookingErrorDomainException(errors);
		}
		if (bookingRequest.getCreateAt() == null) {
			bookingRequest.setCreateAt(new Date());
		}
		return bookingRequestDAO.save(bookingRequest);
	}

	private List<BookingRequestError> validateBookingRequest(final BookingRequest bookingRequest) {
		List<BookingRequestError> errors = new ArrayList<>();
		return errors;
	}

}
