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
import com.sahydo.bookingapp.utilities.DateTimeUtil;
import com.sahydo.bookingapp.utilities.EmailTemplateUtil;

@Service
public class BookingRequestImplService implements IBookingRequestService {

	@Autowired
	private IBookingRequestDAO bookingRequestDAO;
	@Autowired
	private IEmailService emailService;

	@Override
	@Transactional(readOnly = true) // To sync with db
	public List<BookingRequest> findAll() {
		return (List<BookingRequest>) bookingRequestDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true) // To sync with db
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
			emailService.sendSimpleFailureEmail(bookingRequest.getHolderEmail(), EmailTemplateUtil.BOOKING_FAILURE_CREATE_SUBJECT,
					errors);
			throw new BookingErrorDomainException(errors);
		}
		if (bookingRequest.getCreateAt() == null) {
			bookingRequest.setCreateAt(new Date());
		}
		bookingRequest.setTotalDays(DateTimeUtil.diffInDays(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()));
		System.out.println(bookingRequest.getTotalDays());
		BookingRequest bookingRequestSaved = bookingRequestDAO.save(bookingRequest);
		if (bookingRequestSaved != null) {
			emailService.sendSimpleEmail(bookingRequest.getHolderEmail(), EmailTemplateUtil.BOOKING_SUCCESSFULLY_CREATED_SUBJECT,
					"The Booking request was created successfully. Please check the details below: \n"
							+ bookingRequest.toString());
		}
		return bookingRequestSaved;
	}

	private List<BookingRequestError> validateBookingRequest(final BookingRequest bookingRequest) {
		List<BookingRequestError> errors = new ArrayList<>();
		Date currentDate = new Date();
		if (bookingRequest.getCheckInDate().before(currentDate)) {
			errors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD, "Checkin Date",
					"The Checkin date must be greater than current date"));
		}
		if (bookingRequest.getCheckOutDate().before(bookingRequest.getCheckInDate())) {
			errors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD, "Checkout Date",
					"The Checkout date must be greater than Checkin date"));
		}
		if (bookingRequest.getNumberOfMinors() >= bookingRequest.getNumberOfPeople()) {
			errors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD, "Number of People",
					"Number of minors must be less than Number of People"));
		}
		return errors;
	}

}
