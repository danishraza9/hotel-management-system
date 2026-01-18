package com.hotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Room class.
 * Tests cover normal cases, boundary cases, and invalid inputs.
 */
@DisplayName("Room Tests")
class RoomTest {
    
    private Room room;
    
    @BeforeEach
    void setUp() {
        room = new Room("101", RoomType.DOUBLE, 150.0);
    }
    
    @Test
    @DisplayName("Should create room with valid parameters")
    void testRoomCreation() {
        // Arrange & Act
        Room testRoom = new Room("102", RoomType.SINGLE, 100.0);
        
        // Assert
        assertEquals("102", testRoom.getRoomNumber());
        assertEquals(RoomType.SINGLE, testRoom.getType());
        assertEquals(100.0, testRoom.getPricePerNight());
        assertEquals(RoomStatus.AVAILABLE, testRoom.getStatus());
    }
    
    @Test
    @DisplayName("Should throw exception when room number is null")
    void testRoomNumberNull() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Room(null, RoomType.DOUBLE, 100.0));
    }
    
    @Test
    @DisplayName("Should throw exception when room number is empty")
    void testRoomNumberEmpty() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Room("", RoomType.DOUBLE, 100.0));
    }
    
    @Test
    @DisplayName("Should throw exception when room type is null")
    void testRoomTypeNull() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Room("101", null, 100.0));
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -100.0, -0.01})
    @DisplayName("Should throw exception for negative prices")
    void testNegativePrice(double negativePrice) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Room("101", RoomType.DOUBLE, negativePrice));
    }
    
    @Test
    @DisplayName("Should accept zero price")
    void testZeroPrice() {
        // Arrange & Act
        Room testRoom = new Room("103", RoomType.SINGLE, 0.0);
        
        // Assert
        assertEquals(0.0, testRoom.getPricePerNight());
    }
    
    @Test
    @DisplayName("Should return true when room is available")
    void testIsAvailable() {
        // Arrange
        room.setStatus(RoomStatus.AVAILABLE);
        
        // Act & Assert
        assertTrue(room.isAvailable());
    }
    
    @Test
    @DisplayName("Should return false when room is not available")
    void testIsNotAvailable() {
        // Arrange
        room.setStatus(RoomStatus.OCCUPIED);
        
        // Act & Assert
        assertFalse(room.isAvailable());
    }
    
    @Test
    @DisplayName("Should set room status")
    void testSetStatus() {
        // Arrange & Act
        room.setStatus(RoomStatus.MAINTENANCE);
        
        // Assert
        assertEquals(RoomStatus.MAINTENANCE, room.getStatus());
    }
    
    @Test
    @DisplayName("Should throw exception when setting null status")
    void testSetNullStatus() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> room.setStatus(null));
    }
    
    @Test
    @DisplayName("Should calculate total cost correctly")
    void testCalculateTotalCost() {
        // Arrange
        room = new Room("101", RoomType.DOUBLE, 100.0);
        
        // Act
        double totalCost = room.calculateTotalCost(3);
        
        // Assert
        assertEquals(300.0, totalCost);
    }
    
    @ParameterizedTest
    @ValueSource(longs = {1, 5, 10, 365})
    @DisplayName("Should calculate cost for various night counts")
    void testCalculateTotalCostMultipleDays(long nights) {
        // Arrange
        double pricePerNight = 100.0;
        room = new Room("104", RoomType.DOUBLE, pricePerNight);
        
        // Act
        double totalCost = room.calculateTotalCost(nights);
        
        // Assert
        assertEquals(pricePerNight * nights, totalCost);
    }
    
    @Test
    @DisplayName("Should throw exception for zero nights")
    void testCalculateCostZeroNights() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> room.calculateTotalCost(0));
    }
    
    @ParameterizedTest
    @ValueSource(longs = {-1, -10, -365})
    @DisplayName("Should throw exception for negative nights")
    void testCalculateCostNegativeNights(long negativeNights) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> room.calculateTotalCost(negativeNights));
    }
    
    @Test
    @DisplayName("Should set and get description")
    void testDescription() {
        // Arrange
        String description = "Sea view with balcony";
        
        // Act
        room.setDescription(description);
        
        // Assert
        assertEquals(description, room.getDescription());
    }
    
    @Test
    @DisplayName("Should throw exception when setting null description")
    void testSetNullDescription() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> room.setDescription(null));
    }
    
    @Test
    @DisplayName("Should consider equal rooms with same room number")
    void testEquality() {
        // Arrange
        Room room1 = new Room("101", RoomType.DOUBLE, 100.0);
        Room room2 = new Room("101", RoomType.SINGLE, 200.0);
        
        // Act & Assert
        assertEquals(room1, room2);
    }
    
    @Test
    @DisplayName("Should have consistent hash codes for equal rooms")
    void testHashCode() {
        // Arrange
        Room room1 = new Room("101", RoomType.DOUBLE, 100.0);
        Room room2 = new Room("101", RoomType.SINGLE, 200.0);
        
        // Act & Assert
        assertEquals(room1.hashCode(), room2.hashCode());
    }
    
    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        // Act
        String result = room.toString();
        
        // Assert
        assertTrue(result.contains("101"));
        assertTrue(result.contains("Double Room"));
        assertTrue(result.contains("150"));
    }
}
