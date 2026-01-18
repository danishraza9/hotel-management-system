package com.hotel.service;

import com.hotel.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Service class for managing hotel operations.
 * Handles availability checking and room management.
 */
public class HotelService {
    
    private final Hotel hotel;
    
    /**
     * Creates a HotelService for the given hotel.
     *
     * @param hotel the hotel to manage (non-null)
     * @throws IllegalArgumentException if hotel is null
     */
    public HotelService(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null");
        }
        this.hotel = hotel;
    }
    
    /**
     * Gets hotel information.
     *
     * @return the hotel object
     */
    public Hotel getHotel() {
        return hotel;
    }
    
    /**
     * Checks room availability for a specific date range.
     *
     * @param checkInDate check-in date (non-null)
     * @param checkOutDate check-out date (non-null)
     * @return list of available rooms
     * @throws IllegalArgumentException if dates are invalid
     */
    public List<Room> checkAvailability(LocalDate checkInDate, LocalDate checkOutDate) {
        Objects.requireNonNull(checkInDate, "Check-in date cannot be null");
        Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");
        
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : hotel.getAvailableRooms()) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    
    /**
     * Gets available rooms by type.
     *
     * @param roomType type of room (non-null)
     * @return list of available rooms of the specified type
     */
    public List<Room> getAvailableRoomsByType(RoomType roomType) {
        Objects.requireNonNull(roomType, "Room type cannot be null");
        
        List<Room> result = new ArrayList<>();
        for (Room room : hotel.getAvailableRooms()) {
            if (room.getType() == roomType) {
                result.add(room);
            }
        }
        return result;
    }
    
    /**
     * Gets rooms by their status.
     *
     * @param status room status (non-null)
     * @return list of rooms with the specified status
     */
    public List<Room> getRoomsByStatus(RoomStatus status) {
        Objects.requireNonNull(status, "Room status cannot be null");
        
        List<Room> result = new ArrayList<>();
        for (Room room : hotel.getAllRooms()) {
            if (room.getStatus() == status) {
                result.add(room);
            }
        }
        return result;
    }
    
    /**
     * Gets the average price of available rooms.
     *
     * @return average price or 0.0 if no available rooms
     */
    public double getAveragePriceOfAvailableRooms() {
        List<Room> availableRooms = hotel.getAvailableRooms();
        if (availableRooms.isEmpty()) {
            return 0.0;
        }
        
        double totalPrice = 0.0;
        for (Room room : availableRooms) {
            totalPrice += room.getPricePerNight();
        }
        return totalPrice / availableRooms.size();
    }
    
    /**
     * Finds the cheapest available room.
     *
     * @return the cheapest room or null if no available rooms
     */
    public Room findCheapestAvailableRoom() {
        List<Room> availableRooms = hotel.getAvailableRooms();
        if (availableRooms.isEmpty()) {
            return null;
        }
        
        Room cheapest = availableRooms.get(0);
        for (Room room : availableRooms) {
            if (room.getPricePerNight() < cheapest.getPricePerNight()) {
                cheapest = room;
            }
        }
        return cheapest;
    }
    
    /**
     * Finds the most expensive available room.
     *
     * @return the most expensive room or null if no available rooms
     */
    public Room findMostExpensiveAvailableRoom() {
        List<Room> availableRooms = hotel.getAvailableRooms();
        if (availableRooms.isEmpty()) {
            return null;
        }
        
        Room mostExpensive = availableRooms.get(0);
        for (Room room : availableRooms) {
            if (room.getPricePerNight() > mostExpensive.getPricePerNight()) {
                mostExpensive = room;
            }
        }
        return mostExpensive;
    }
    
    /**
     * Gets occupancy rate of the hotel.
     *
     * @return occupancy rate as percentage (0-100)
     */
    public double getOccupancyRate() {
        int totalRooms = hotel.getTotalRoomCount();
        if (totalRooms == 0) {
            return 0.0;
        }
        
        long occupiedCount = hotel.getAllRooms().stream()
                .filter(r -> r.getStatus() == RoomStatus.OCCUPIED)
                .count();
        
        return (occupiedCount * 100.0) / totalRooms;
    }
}
