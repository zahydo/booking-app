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
import com.sahydo.bookingapp.presentation.rest.exception.EnumErrorCode;
import com.sahydo.bookingapp.presentation.rest.exception.ResourceNotFoundException;

@Service
public class BookingRequestImplService implements IBookingRequestService {

	@Autowired
	private IBookingRequestDAO bookingRequestDAO;
	@Autowired
	private IEmailService emailService;

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
			throw new ResourceNotFoundException(id);
		}
		return bookingRequest;
	}

	@Override
	@Transactional
	public BookingRequest create(BookingRequest bookingRequest) throws BookingErrorDomainException {
		List<BookingRequestError> errors = validateBookingRequest(bookingRequest);
		if (!errors.isEmpty()) {
			emailService.sendSimpleFailureEmail(bookingRequest.getHolderEmail(), "Problems With Booking", errors);
			throw new BookingErrorDomainException(errors);
		}
		if (bookingRequest.getCreateAt() == null) {
			bookingRequest.setCreateAt(new Date());
		}
		BookingRequest bookingRequestSaved = bookingRequestDAO.save(bookingRequest);
		if (bookingRequestSaved != null) {
			emailService.sendSimpleEmail(bookingRequest.getHolderEmail(), "Booking Request created successfully", "The Booking request was created successfully. Please check the details below");
		}
		return bookingRequestSaved;
	}

	private List<BookingRequestError> validateBookingRequest(final BookingRequest bookingRequest) {
		List<BookingRequestError> errors = new ArrayList<>();
		Date currentDate = new Date();
		if (bookingRequest.getCheckInDate().before(currentDate)) {
			errors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD, "Checkin Date", "The Checkin date must be greater than current date"));
		}
		if (bookingRequest.getCheckOutDate().before(bookingRequest.getCheckInDate())) {
			errors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD, "Checkout Date", "The Checkout date must be greater than Checkin date"));
		}
		if (bookingRequest.getNumberOfMinors() >= bookingRequest.getNumberOfPeople()) {
			errors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD, "Number of People", "Number of minors must be less than Number of People"));
		}
		return errors;
	}

}
