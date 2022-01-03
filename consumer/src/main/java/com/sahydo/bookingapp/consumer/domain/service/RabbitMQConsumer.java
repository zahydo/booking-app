package com.sahydo.bookingapp.consumer.domain.service;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.sahydo.bookingapp.consumer.domain.model.BookingRequest;

@Component
public class RabbitMQConsumer {

	@Autowired
	private IBookingRequestConsumerService bookingServiceConsumer;

	@RabbitListener(queues = "bookingrequest.queue")
	public void recievedMessage(BookingRequest bookingRequest) {
		try {
			bookingServiceConsumer.sendBookingRequest(bookingRequest);
		} catch (HttpClientErrorException e) {
			System.err.println(e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
}