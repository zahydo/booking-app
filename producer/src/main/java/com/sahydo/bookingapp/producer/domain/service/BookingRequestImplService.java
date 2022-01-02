package com.sahydo.bookingapp.producer.domain.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sahydo.bookingapp.producer.domain.model.BookingRequest;
import com.sahydo.bookingapp.producer.utilities.BookingRequestsConstants;

@Service
public class BookingRequestImplService implements IBookingRequestService {

	@Autowired
	private RabbitMQSender rabbitMQSender;
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<BookingRequest> findAll() {
		try {
			String url = BookingRequestsConstants.BOOKING_API_URL;
			BookingRequest[] array = restTemplate.getForObject(url,
					BookingRequest[].class);
			return Arrays.asList(array);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	@Override
	public BookingRequest findById(Long id) {
		return restTemplate.getForObject(BookingRequestsConstants.BOOKING_API_URL + "/" + id, BookingRequest.class);
	}

	@Override
	public BookingRequest create(BookingRequest bookingRequest) {
		rabbitMQSender.send(bookingRequest);
		return bookingRequest;
	}

}
