package com.sahydo.bookingapp.producer.domain.model;

import java.io.Serializable;

import lombok.Data;

public @Data class BookingRequestLink implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rel;
	private String href;
}