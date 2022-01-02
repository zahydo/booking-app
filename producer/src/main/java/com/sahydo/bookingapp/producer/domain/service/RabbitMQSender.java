package com.sahydo.bookingapp.producer.domain.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahydo.bookingapp.producer.domain.model.BookingRequest;
import com.sahydo.bookingapp.producer.utilities.BookingRequestsConstants;


@Service
public class RabbitMQSender {


	@Autowired
	private AmqpTemplate amqpTemplate;

	public void send(BookingRequest bookingRequest) {
		amqpTemplate.convertAndSend(BookingRequestsConstants.DIRECT_EXCHANGE_NAME, BookingRequestsConstants.QUEUE_NAME, bookingRequest);
	}
}