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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
	@NotNull(message = "Checkin Date is required")
	private Date checkInDate;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
	@NotNull(message = "Checkout Date is required")
	private Date checkOutDate;
	@NotNull(message = "Total Days is required")
	@Min(value = 1, message = "Total Days must be greater than 0")
	private Integer totalDays;
	@NotNull(message = "Holder Name is required")
	private String holderName;
	@NotNull(message = "Number of People is required")
	@Min(value = 1, message = "Number of People must be greater than 0")
	private Integer numberOfPeople;
	private Integer numberOfRooms = 1;
	private Integer numberOfMinors = 0;
}
