package com.sahydo.bookingapp.producer.domain.model;

import java.io.Serializable;

public class BookingRequestLinkSelf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Self self;
}

class Self implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String href;
}