package com.hotel.model;

import java.util.Objects;

/**
 * Represents a room in the hotel.
 * Mutable entity that tracks room status and pricing.
 */
public class Room {
    
    private final String roomNumber;
    private final RoomType type;
    private final double pricePerNight;
    private RoomStatus status;
    private String description;
    
    /**
     * Creates a new Room with specified details.
     *
     * @param roomNumber unique room identifier (non-null, non-empty)
     * @param type room type (non-null)
     * @param pricePerNight price per night (non-negative)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Room(String roomNumber, RoomType type, double pricePerNight) {
        this.roomNumber = validateRoomNumber(roomNumber);
        this.type = Objects.requireNonNull(type, "Room type cannot be null");
        this.pricePerNight = validatePrice(pricePerNight);
        this.status = RoomStatus.AVAILABLE;
        this.description = "";
    }
    
    private String validateRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        return roomNumber.trim();
    }
    
    private double validatePrice(double pricePerNight) {
        if (pricePerNight < 0) {
            throw new IllegalArgumentException("Price per night cannot be negative");
        }
        return pricePerNight;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public RoomType getType() {
        return type;
    }
    
    public double getPricePerNight() {
        return pricePerNight;
    }
    
    public RoomStatus getStatus() {
        return status;
    }
    
    /**
     * Sets the room status.
     *
     * @param status new room status (non-null)
     * @throws IllegalArgumentException if status is null
     */
    public void setStatus(RoomStatus status) {
        this.status = Objects.requireNonNull(status, "Room status cannot be null");
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the room description.
     *
     * @param description room description
     * @throws IllegalArgumentException if description is null
     */
    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "Description cannot be null");
    }
    
    /**
     * Checks if the room is available for booking.
     *
     * @return true if room is available, false otherwise
     */
    public boolean isAvailable() {
        return status == RoomStatus.AVAILABLE;
    }
    
    /**
     * Calculates the total cost for a given number of nights.
     *
     * @param numberOfNights number of nights (positive)
     * @return total cost
     * @throws IllegalArgumentException if numberOfNights is not positive
     */
    public double calculateTotalCost(long numberOfNights) {
        if (numberOfNights <= 0) {
            throw new IllegalArgumentException("Number of nights must be positive");
        }
        return pricePerNight * numberOfNights;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
    
    @Override
    public String toString() {
        return String.format("Room{number=%s, type=%s, price=%.2f, status=%s}",
                roomNumber, type.getDisplayName(), pricePerNight, status.getDisplayName());
    }
}
