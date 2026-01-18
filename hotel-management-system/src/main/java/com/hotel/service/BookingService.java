package com.hotel.service;

import com.hotel.exception.InvalidBookingException;
import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Service class for managing hotel bookings.
 * Handles booking creation, cancellation, and retrieval operations.
 */
public class BookingService {
    
    private final List<Booking> bookings;
    private final Hotel hotel;
    
    /**
     * Creates a BookingService for the given hotel.
     *
     * @param hotel the hotel to manage bookings for (non-null)
     * @throws IllegalArgumentException if hotel is null
     */
    public BookingService(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null");
        }
        this.hotel = hotel;
        this.bookings = new ArrayList<>();
    }
    
    /**
     * Creates a new booking for a guest.
     *
     * @param bookingId unique booking identifier (non-null, non-empty)
     * @param guestName guest name (non-null, non-empty)
     * @param roomNumber room number (non-null, non-empty)
     * @param checkInDate check-in date (non-null)
     * @param checkOutDate check-out date (non-null)
     * @return the created booking
     * @throws InvalidBookingException if booking details are invalid
     * @throws RoomNotAvailableException if room is not available
     */
    public Booking createBooking(String bookingId, String guestName, String roomNumber,
                                 LocalDate checkInDate, LocalDate checkOutDate)
            throws InvalidBookingException, RoomNotAvailableException {
        
        validateBookingInput(bookingId, guestName, roomNumber, checkInDate, checkOutDate);
        
        Room room = hotel.getRoomByNumber(roomNumber);
        if (room == null) {
            throw new InvalidBookingException("Room not found: " + roomNumber);
        }
        
        if (!room.isAvailable()) {
            throw new RoomNotAvailableException("Room " + roomNumber + " is not available");
        }
        
        if (!isRoomAvailableForDates(roomNumber, checkInDate, checkOutDate)) {
            throw new RoomNotAvailableException("Room " + roomNumber + " is not available for the specified dates");
        }
        
        double totalPrice = calculateTotalPrice(room, checkInDate, checkOutDate);
        
        Booking booking = new Booking(bookingId, guestName, room, checkInDate, checkOutDate,
                totalPrice, BookingStatus.CONFIRMED);
        
        bookings.add(booking);
        room.setStatus(RoomStatus.OCCUPIED);
        
        return booking;
    }
    
    private void validateBookingInput(String bookingId, String guestName, String roomNumber,
                                     LocalDate checkInDate, LocalDate checkOutDate)
            throws InvalidBookingException {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new InvalidBookingException("Booking ID cannot be null or empty");
        }
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be null or empty");
        }
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new InvalidBookingException("Room number cannot be null or empty");
        }
        if (checkInDate == null) {
            throw new InvalidBookingException("Check-in date cannot be null");
        }
        if (checkOutDate == null) {
            throw new InvalidBookingException("Check-out date cannot be null");
        }
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new InvalidBookingException("Check-in date cannot be in the past");
        }
    }
    
    /**
     * Checks if a room is available for the specified date range.
     *
     * @param roomNumber room number (non-null, non-empty)
     * @param checkInDate check-in date (non-null)
     * @param checkOutDate check-out date (non-null)
     * @return true if room is available, false otherwise
     */
    public boolean isRoomAvailableForDates(String roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        Objects.requireNonNull(checkInDate, "Check-in date cannot be null");
        Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");
        
        return bookings.stream()
                .filter(b -> b.getRoom().getRoomNumber().equals(roomNumber.trim()))
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .noneMatch(b -> hasDateConflict(b, checkInDate, checkOutDate));
    }
    
    private boolean hasDateConflict(Booking existingBooking, LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate existingCheckIn = existingBooking.getCheckInDate();
        LocalDate existingCheckOut = existingBooking.getCheckOutDate();
        
        return !checkOutDate.isBefore(existingCheckIn) && !checkInDate.isAfter(existingCheckOut);
    }
    
    /**
     * Calculates the total price for a booking.
     *
     * @param room the room (non-null)
     * @param checkInDate check-in date (non-null)
     * @param checkOutDate check-out date (non-null)
     * @return total price
     */
    public double calculateTotalPrice(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(checkInDate, "Check-in date cannot be null");
        Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");
        
        long numberOfNights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return room.calculateTotalCost(numberOfNights);
    }
    
    /**
     * Cancels a booking by its ID.
     *
     * @param bookingId booking ID (non-null, non-empty)
     * @return true if booking was cancelled, false if booking not found
     * @throws IllegalArgumentException if bookingId is invalid
     */
    public boolean cancelBooking(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty");
        }
        
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId.trim())) {
                if (booking.getStatus() != BookingStatus.CANCELLED) {
                    booking.getRoom().setStatus(RoomStatus.AVAILABLE);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    
    /**
     * Gets a booking by its ID.
     *
     * @param bookingId booking ID (non-null, non-empty)
     * @return the booking if found, null otherwise
     */
    public Booking getBookingById(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty");
        }
        
        return bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId.trim()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Gets all bookings for a specific guest.
     *
     * @param guestName guest name (non-null, non-empty)
     * @return list of bookings for the guest
     */
    public List<Booking> getBookingsByGuest(String guestName) {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be null or empty");
        }
        
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getGuestName().equalsIgnoreCase(guestName.trim())) {
                result.add(booking);
            }
        }
        return result;
    }
    
    /**
     * Gets all active bookings (confirmed and not cancelled).
     *
     * @return list of active bookings
     */
    public List<Booking> getActiveBookings() {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                result.add(booking);
            }
        }
        return result;
    }
    
    /**
     * Gets all bookings (unmodifiable list).
     *
     * @return all bookings
     */
    public List<Booking> getAllBookings() {
        return Collections.unmodifiableList(bookings);
    }
    
    /**
     * Gets the total number of bookings.
     *
     * @return booking count
     */
    public int getTotalBookings() {
        return bookings.size();
    }
}
