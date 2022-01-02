package com.sahydo.bookingapp.producer.domain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author Santiago Hyun
 */
public @Data class BookingRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date createAt;
	private Date checkInDate;
	private Date checkOutDate;
	private Integer totalDays;
	private String holderName;
	private String holderEmail;
	private Integer numberOfPeople;
	private Integer numberOfRooms;
	private Integer numberOfMinors;
}
