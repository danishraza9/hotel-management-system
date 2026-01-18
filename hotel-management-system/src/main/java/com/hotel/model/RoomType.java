package com.hotel.model;

/**
 * Enumeration for room types.
 */
public enum RoomType {
    SINGLE("Single Room", 1),
    DOUBLE("Double Room", 2),
    SUITE("Suite", 4),
    DELUXE("Deluxe Suite", 2);
    
    private final String displayName;
    private final int capacity;
    
    RoomType(String displayName, int capacity) {
        this.displayName = displayName;
        this.capacity = capacity;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getCapacity() {
        return capacity;
    }
}
