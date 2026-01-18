package com.hotel.exception;

/**
 * Exception thrown when room details are invalid.
 */
public class InvalidRoomException extends HotelException {
    
    public InvalidRoomException(String message) {
        super(message);
    }
}
