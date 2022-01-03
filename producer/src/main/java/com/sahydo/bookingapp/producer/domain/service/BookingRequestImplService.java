package com.sahydo.bookingapp.producer.domain.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sahydo.bookingapp.producer.domain.model.BookingRequest;
import com.sahydo.bookingapp.producer.domain.model.BookingRequestWrapper;
import com.sahydo.bookingapp.producer.utilities.BookingRequestsConstants;

@Service
public class BookingRequestImplService implements IBookingRequestService {

	@Autowired
	private RabbitMQSender rabbitMQSender;
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<BookingRequestWrapper> findAll() {
		try {
			String url = BookingRequestsConstants.BOOKING_API_URL;
			ResponseEntity<BookingRequestWrapper[]> response = restTemplate.getForEntity(url, BookingRequestWrapper[].class);
			return Arrays.asList(response.getBody());
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	@Override
	public BookingRequestWrapper findById(Long id) {
		return restTemplate.getForObject(BookingRequestsConstants.BOOKING_API_URL + "/" + id, BookingRequestWrapper.class);
	}

	@Override
	public BookingRequest create(BookingRequest bookingRequest) {
		rabbitMQSender.send(bookingRequest);
		return bookingRequest;
	}

}
