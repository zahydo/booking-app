package com.sahydo.bookingapp.presentation.rest;

import java.util.ArrayList;
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

import com.sahydo.bookingapp.domain.service.IBookingRequestService;
import com.sahydo.bookingapp.presentation.rest.exception.ResourceNotFoundException;
import com.sahydo.bookingapp.domain.model.BookingRequest;
import com.sahydo.bookingapp.domain.model.wrapper.BookingRequestWrapper;
import com.sahydo.bookingapp.presentation.rest.exception.BookingErrorDomainException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
	public HttpEntity<List<BookingRequestWrapper>> findAll() {
		List<BookingRequestWrapper> bookingRequests = new ArrayList<>();
		bookingRequestService.findAll().stream().forEach(br -> {
			BookingRequestWrapper wrapper = new BookingRequestWrapper(br);
			try {
				wrapper.add(linkTo(methodOn(BookingRequestController.class).findById(br.getId())).withSelfRel());
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
			bookingRequests.add(wrapper);
		});
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
	public HttpEntity<BookingRequestWrapper> findById(@PathVariable Long id) throws ResourceNotFoundException {
		BookingRequest bookingRequest = bookingRequestService.findById(id);
		BookingRequestWrapper wrapper = new BookingRequestWrapper(bookingRequest);
		wrapper.add(linkTo(methodOn(BookingRequestController.class).findById(id)).withSelfRel());
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	/**
	 * Creates a BookingRequest
	 * 
	 * @param BookingRequest JSON
	 * @return BookingRequesto JSON
	 */

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HttpEntity<BookingRequestWrapper> create(@RequestBody BookingRequest bookingRequest)
			throws BookingErrorDomainException {
		BookingRequest bookingRequestCreated = bookingRequestService.create(bookingRequest);
		BookingRequestWrapper wrapper = new BookingRequestWrapper(bookingRequestCreated);
		wrapper.add(linkTo(methodOn(BookingRequestController.class).create(bookingRequest)).withSelfRel());
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

}
