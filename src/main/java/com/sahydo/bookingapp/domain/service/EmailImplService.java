package com.sahydo.bookingapp.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sahydo.bookingapp.presentation.rest.exception.BookingRequestError;

@Service
public class EmailImplService implements IEmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void sendSimpleEmail(final String to, final String subject, final String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@bogus.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		emailSender.send(message);
	}

	@Override
	public void sendSimpleFailureEmail(String to, String subject, List<BookingRequestError> errors) {
		sendSimpleEmail(to, subject, getErrorMessageFromBookingErrors(errors));
	}
	
	private String getErrorMessageFromBookingErrors(List<BookingRequestError> errors) {
		String message = "The Booking Request was not processed due to the next problems: \n";
		List<String> errorMessages = errors.stream().map(error -> "\n" + error.message.concat(" ")).collect(Collectors.toList());
		return message.concat(String.join(" ", errorMessages));
	}

}
