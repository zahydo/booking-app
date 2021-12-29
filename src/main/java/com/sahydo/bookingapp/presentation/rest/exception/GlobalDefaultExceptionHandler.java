package com.sahydo.bookingapp.presentation.rest.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahydo.bookingapp.domain.model.BookingRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@RequestMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<ErrorsPayload> handleResourceNotFoundException(ResourceNotFoundException e) {
		List<BookingRequestError> bookingErrors = new ArrayList<>();
		bookingErrors.add(new BookingRequestError(EnumErrorCode.NOT_FOUND, BookingRequest.class.getSimpleName(), e.getMessage()));
		return new ResponseEntity<>(new ErrorsPayload(bookingErrors), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BookingErrorDomainException.class)
	@RequestMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<ErrorsPayload> handleBookingErrorDomainException(BookingErrorDomainException e) {
		return new ResponseEntity<>(new ErrorsPayload(e.errors), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@RequestMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<ErrorsPayload> handleConstrainViolationException(ConstraintViolationException error) {
		List<BookingRequestError> bookingErrors = new ArrayList<>();
		error.getConstraintViolations().stream().forEach(violation -> {
			bookingErrors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD,
					violation.getPropertyPath().toString(), violation.getMessage()));
		});
		return new ResponseEntity<>(new ErrorsPayload(bookingErrors), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@RequestMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<ErrorsPayload> handleHttpMessageNotReadableException(HttpMessageNotReadableException error) {
		List<BookingRequestError> bookingErrors = new ArrayList<>();
		bookingErrors.add(new BookingRequestError(EnumErrorCode.INVALID_FIELD, BookingRequest.class.getSimpleName(), error.getMessage()));
		return new ResponseEntity<>(new ErrorsPayload(bookingErrors), HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
