package com.sahydo.bookingapp.consumer.domain.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.sahydo.bookingapp.consumer.domain.model.BookingRequest;
import com.sahydo.bookingapp.consumer.utilities.BookingRequestsConstants;
import com.sahydo.bookingapp.consumer.utilities.DateTimeUtil;

@Service
public class BookingRequestConsumerImplService implements IBookingRequestConsumerService {

	@Override
	public void sendBookingRequest(BookingRequest bookingRequest) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JsonObject json = new JsonObject();
		json.addProperty("checkInDate", DateTimeUtil.dateFormatYYYYMMDD(bookingRequest.getCheckInDate()));
		json.addProperty("checkOutDate", DateTimeUtil.dateFormatYYYYMMDD(bookingRequest.getCheckOutDate()));
		json.addProperty("totalDays",
				bookingRequest.getTotalDays() != null ? bookingRequest.getTotalDays().toString() : "");
		json.addProperty("numberOfPeople",
				bookingRequest.getNumberOfPeople() != null ? bookingRequest.getNumberOfPeople().toString() : "");
		json.addProperty("numberOfRooms",
				bookingRequest.getNumberOfRooms() != null ? bookingRequest.getNumberOfRooms().toString() : "");
		json.addProperty("numberOfMinors",
				bookingRequest.getNumberOfMinors() != null ? bookingRequest.getNumberOfMinors().toString() : "");
		json.addProperty("holderName", bookingRequest.getHolderName() != null ? bookingRequest.getHolderName() : "");
		json.addProperty("holderEmail", bookingRequest.getHolderEmail() != null ? bookingRequest.getHolderEmail() : "");
		HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(BookingRequestsConstants.BOOKING_API_URL,
				HttpMethod.POST, entity, String.class);
		System.out.println(response);
	}

}
