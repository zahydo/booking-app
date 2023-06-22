package com.sahydo.bookingapp.utilities;

public class EmailTemplateUtil {

    private EmailTemplateUtil() {
        
    }
	public static final String BOOKING_SUCCESSFULLY_CREATED_SUBJECT = "Booking Request created successfully";
	public static final String BOOKING_FAILURE_CREATE_SUBJECT = "Problems With Booking";
    public static final boolean IS_ENABLED_EMAILING =  System.getenv("IS_ENABLED_EMAILING") != null && System.getenv("IS_ENABLED_EMAILING").equals("true");

}
