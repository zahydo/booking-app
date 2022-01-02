package com.sahydo.bookingapp.producer.utilities;

public class BookingRequestsConstants {
	public static final String DIRECT_EXCHANGE_NAME = "bookingrequest-direct-exchange";
	public static final String DEAD_LETTER_EXCHANGE_NAME = "deadLetterExchange";
	public static final String DEAD_LETTER_ROUTING_KEY_NAME = "deadLetter";
	public static final String QUEUE_NAME = "bookingrequest";
	public static final String BOOKING_REQUEST_ENDPOINT = "/bookingRequests";
	public static final String BOOKING_API_URL = (System.getenv("BOOKING_API_URL") != null ? System.getenv("BOOKING_API_URL") : "http://localhost:8080") + BOOKING_REQUEST_ENDPOINT;
	
}
