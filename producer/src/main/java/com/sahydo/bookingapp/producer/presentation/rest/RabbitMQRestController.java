package com.sahydo.bookingapp.producer.presentation.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sahydo.bookingapp.producer.domain.model.BookingRequest;
import com.sahydo.bookingapp.producer.domain.model.BookingRequestWrapper;
import com.sahydo.bookingapp.producer.domain.service.IBookingRequestService;


@RestController
@RequestMapping("bookingRequests")
public class RabbitMQRestController {
	

	@Autowired
	private IBookingRequestService bookingRequestService;

	/**
	 * 
	 * @return list of Booking Requests
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public HttpEntity<List<BookingRequestWrapper>> findAll() {
		List<BookingRequestWrapper> bookingRequests = bookingRequestService.findAll();
		return new ResponseEntity<>(bookingRequests, HttpStatus.OK);
	}

	/**
	 * get a Booking Request by Id
	 * 
	 * @param id
	 * @return BookingRequest JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public HttpEntity<BookingRequestWrapper> findById(@PathVariable Long id) {
		BookingRequestWrapper bookingRequest = bookingRequestService.findById(id);
		return new ResponseEntity<>(bookingRequest, HttpStatus.OK);
	}

	/**
	 * Creates a BookingRequest
	 * 
	 * @param BookingRequest JSON
	 * @return BookingRequesto JSON
	 * @throws ResourceNotFoundException 
	 */

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HttpEntity<String> create(@RequestBody BookingRequest bookingRequest) {
		BookingRequest bookingRequestCreated = bookingRequestService.create(bookingRequest);
		return new ResponseEntity<>("{status: sent, message: 'Booking request was sent successfully, check your email for response " + bookingRequestCreated.getHolderEmail() + "'}", HttpStatus.OK);
	}

}
