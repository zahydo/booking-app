package com.sahydo.bookingapp.presentation.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sahydo.bookingapp.domain.service.IBookingRequestService;
import com.sahydo.bookingapp.presentation.rest.exception.ResourceNotFoundException;
import com.sahydo.bookingapp.domain.model.BookingRequest;
import com.sahydo.bookingapp.presentation.rest.exception.BookingErrorDomainException;

/**
 * @author Santiago Hyun
 */

@RestController
@RequestMapping("bookingRequests")
public class BookingRequestController {
	@Autowired
	private IBookingRequestService bookingRequestService;

	/**
	 * 
	 * @return list of Booking Requests
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BookingRequest> findAll() {
		return (List<BookingRequest>) bookingRequestService.findAll();
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
	public BookingRequest findById(@PathVariable Long id) throws ResourceNotFoundException {
		return bookingRequestService.findById(id);
	}

	/**
	 * Creates a BookingRequest
	 * 
	 * @param BookingRequest JSON
	 * @return BookingRequesto JSON
	 */

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BookingRequest create(@RequestBody BookingRequest BookingRequest) throws BookingErrorDomainException {
		return bookingRequestService.create(BookingRequest);
	}

}
