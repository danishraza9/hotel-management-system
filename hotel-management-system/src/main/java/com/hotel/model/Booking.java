package com.hotel.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a booking in the hotel system.
 * Immutable value object for booking details.
 */
public final class Booking {
    
    private final String bookingId;
    private final String guestName;
    private final Room room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final double totalPrice;
    private final BookingStatus status;
    
    /**
     * Creates a new Booking with specified details.
     *
     * @param bookingId unique booking identifier (non-null, non-empty)
     * @param guestName name of the guest (non-null, non-empty)
     * @param room the booked room (non-null)
     * @param checkInDate check-in date (non-null, not in the past)
     * @param checkOutDate check-out date (non-null, after check-in)
     * @param totalPrice total booking price (non-negative)
     * @param status current booking status (non-null)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Booking(String bookingId, String guestName, Room room, 
                   LocalDate checkInDate, LocalDate checkOutDate, 
                   double totalPrice, BookingStatus status) {
        this.bookingId = validateBookingId(bookingId);
        this.guestName = validateGuestName(guestName);
        this.room = Objects.requireNonNull(room, "Room cannot be null");
        this.checkInDate = Objects.requireNonNull(checkInDate, "Check-in date cannot be null");
        this.checkOutDate = Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");
        this.totalPrice = validatePrice(totalPrice);
        this.status = Objects.requireNonNull(status, "Booking status cannot be null");
        
        validateDateRange();
    }
    
    private String validateBookingId(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty");
        }
        return bookingId.trim();
    }
    
    private String validateGuestName(String guestName) {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be null or empty");
        }
        if (guestName.length() < 2) {
            throw new IllegalArgumentException("Guest name must be at least 2 characters");
        }
        return guestName.trim();
    }
    
    private double validatePrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        return totalPrice;
    }
    
    private void validateDateRange() {
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }
    
    public String getBookingId() {
        return bookingId;
    }
    
    public String getGuestName() {
        return guestName;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public BookingStatus getStatus() {
        return status;
    }
    
    /**
     * Calculates the number of nights for this booking.
     *
     * @return number of nights
     */
    public long getNumberOfNights() {
        return java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId) &&
               Objects.equals(guestName, booking.guestName) &&
               Objects.equals(checkInDate, booking.checkInDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(bookingId, guestName, checkInDate);
    }
    
    @Override
    public String toString() {
        return String.format("Booking{id=%s, guest=%s, room=%s, checkIn=%s, checkOut=%s, price=%.2f, status=%s}",
                bookingId, guestName, room.getRoomNumber(), checkInDate, checkOutDate, totalPrice, status);
    }
}
