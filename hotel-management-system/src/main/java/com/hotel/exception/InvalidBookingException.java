package com.hotel.exception;

/**
 * Exception thrown when booking details are invalid.
 */
public class InvalidBookingException extends HotelException {
    
    public InvalidBookingException(String message) {
        super(message);
    }
}
