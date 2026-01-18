package com.hotel.exception;

/**
 * Exception thrown when a room is not available for booking.
 */
public class RoomNotAvailableException extends HotelException {
    
    public RoomNotAvailableException(String message) {
        super(message);
    }
}
