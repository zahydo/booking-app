package com.sahydo.bookingapp.domain.service;

import java.util.List;

import com.sahydo.bookingapp.presentation.rest.exception.BookingRequestError;

public interface IEmailService {
	public void sendSimpleEmail(String to, String subject, String body);
	public void sendSimpleFailureEmail(String to, String subject, List<BookingRequestError> errors);
}
