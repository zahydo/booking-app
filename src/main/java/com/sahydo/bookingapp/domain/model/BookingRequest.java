package com.sahydo.bookingapp.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * @author Santiago Hyun
 * */
@Entity
@Table(name = "bookingRequests")
public @Data class BookingRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	@Temporal(TemporalType.DATE)
	private Date checkInDate;
	@Temporal(TemporalType.DATE)
	private Date checkOutDate;
	private Integer totalDays;
	private String holderName;
	private Integer numberOfPeople;
	private Integer numberOfRooms;
	private Integer numberOfMinors;
}
