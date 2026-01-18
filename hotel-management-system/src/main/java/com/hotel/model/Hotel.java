package com.hotel.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a hotel with multiple rooms.
 * Manages hotel details and maintains collection of rooms.
 */
public class Hotel {
    
    private final String hotelId;
    private final String hotelName;
    private final String location;
    private final List<Room> rooms;
    private int starRating;
    
    /**
     * Creates a new Hotel with specified details.
     *
     * @param hotelId unique hotel identifier (non-null, non-empty)
     * @param hotelName name of the hotel (non-null, non-empty)
     * @param location hotel location (non-null, non-empty)
     * @param starRating star rating between 1-5
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Hotel(String hotelId, String hotelName, String location, int starRating) {
        this.hotelId = validateHotelId(hotelId);
        this.hotelName = validateHotelName(hotelName);
        this.location = validateLocation(location);
        this.starRating = validateStarRating(starRating);
        this.rooms = new ArrayList<>();
    }
    
    private String validateHotelId(String hotelId) {
        if (hotelId == null || hotelId.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel ID cannot be null or empty");
        }
        return hotelId.trim();
    }
    
    private String validateHotelName(String hotelName) {
        if (hotelName == null || hotelName.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be null or empty");
        }
        return hotelName.trim();
    }
    
    private String validateLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel location cannot be null or empty");
        }
        return location.trim();
    }
    
    private int validateStarRating(int starRating) {
        if (starRating < 1 || starRating > 5) {
            throw new IllegalArgumentException("Star rating must be between 1 and 5");
        }
        return starRating;
    }
    
    public String getHotelId() {
        return hotelId;
    }
    
    public String getHotelName() {
        return hotelName;
    }
    
    public String getLocation() {
        return location;
    }
    
    public int getStarRating() {
        return starRating;
    }
    
    /**
     * Sets the star rating of the hotel.
     *
     * @param starRating new star rating (1-5)
     * @throws IllegalArgumentException if rating is invalid
     */
    public void setStarRating(int starRating) {
        this.starRating = validateStarRating(starRating);
    }
    
    /**
     * Adds a room to the hotel.
     *
     * @param room the room to add (non-null)
     * @return true if room was added, false if room already exists
     * @throws IllegalArgumentException if room is null
     */
    public boolean addRoom(Room room) {
        Objects.requireNonNull(room, "Room cannot be null");
        
        if (rooms.stream().anyMatch(r -> r.getRoomNumber().equals(room.getRoomNumber()))) {
            return false;
        }
        return rooms.add(room);
    }
    
    /**
     * Removes a room from the hotel.
     *
     * @param roomNumber the room number (non-null, non-empty)
     * @return true if room was removed, false if room not found
     */
    public boolean removeRoom(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        return rooms.removeIf(r -> r.getRoomNumber().equals(roomNumber.trim()));
    }
    
    /**
     * Gets a room by its number.
     *
     * @param roomNumber the room number (non-null, non-empty)
     * @return the room if found, null otherwise
     */
    public Room getRoomByNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        return rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber.trim()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Gets all rooms in the hotel.
     *
     * @return unmodifiable list of rooms
     */
    public List<Room> getAllRooms() {
        return Collections.unmodifiableList(rooms);
    }
    
    /**
     * Gets available rooms in the hotel.
     *
     * @return list of available rooms
     */
    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                available.add(room);
            }
        }
        return available;
    }
    
    /**
     * Gets the total number of rooms.
     *
     * @return total room count
     */
    public int getTotalRoomCount() {
        return rooms.size();
    }
    
    /**
     * Gets the number of available rooms.
     *
     * @return available room count
     */
    public int getAvailableRoomCount() {
        return (int) rooms.stream().filter(Room::isAvailable).count();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(hotelId, hotel.hotelId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(hotelId);
    }
    
    @Override
    public String toString() {
        return String.format("Hotel{id=%s, name=%s, location=%s, rating=%d, rooms=%d}",
                hotelId, hotelName, location, starRating, rooms.size());
    }
}
