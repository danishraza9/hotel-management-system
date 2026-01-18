package com.hotel.exception;

/**
 * Base exception for hotel management system.
 * All domain-specific exceptions extend this class.
 */
public class HotelException extends Exception {
    
    public HotelException(String message) {
        super(message);
    }
    
    public HotelException(String message, Throwable cause) {
        super(message, cause);
    }
}
