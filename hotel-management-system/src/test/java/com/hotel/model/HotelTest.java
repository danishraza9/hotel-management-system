package com.hotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Hotel class.
 */
@DisplayName("Hotel Tests")
class HotelTest {
    
    private Hotel hotel;
    
    @BeforeEach
    void setUp() {
        hotel = new Hotel("H001", "Grand Hotel", "New York", 5);
    }
    
    @Test
    @DisplayName("Should create hotel with valid parameters")
    void testHotelCreation() {
        // Act & Assert
        assertEquals("H001", hotel.getHotelId());
        assertEquals("Grand Hotel", hotel.getHotelName());
        assertEquals("New York", hotel.getLocation());
        assertEquals(5, hotel.getStarRating());
    }
    
    @Test
    @DisplayName("Should throw exception when hotel ID is null")
    void testHotelIdNull() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Hotel(null, "Hotel", "City", 3));
    }
    
    @Test
    @DisplayName("Should throw exception when hotel name is empty")
    void testHotelNameEmpty() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Hotel("H001", "", "City", 3));
    }
    
    @Test
    @DisplayName("Should throw exception when location is null")
    void testLocationNull() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Hotel("H001", "Hotel", null, 3));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, -1, 6, 10})
    @DisplayName("Should throw exception for invalid star ratings")
    void testInvalidStarRating(int rating) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Hotel("H001", "Hotel", "City", rating));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Should accept valid star ratings")
    void testValidStarRatings(int rating) {
        // Act & Assert
        assertDoesNotThrow(
                () -> new Hotel("H001", "Hotel", "City", rating));
    }
    
    @Test
    @DisplayName("Should add room successfully")
    void testAddRoom() {
        // Arrange
        Room room = new Room("101", RoomType.DOUBLE, 100.0);
        
        // Act
        boolean added = hotel.addRoom(room);
        
        // Assert
        assertTrue(added);
        assertEquals(1, hotel.getTotalRoomCount());
    }
    
    @Test
    @DisplayName("Should not add duplicate room")
    void testAddDuplicateRoom() {
        // Arrange
        Room room1 = new Room("101", RoomType.DOUBLE, 100.0);
        Room room2 = new Room("101", RoomType.SINGLE, 150.0);
        hotel.addRoom(room1);
        
        // Act
        boolean added = hotel.addRoom(room2);
        
        // Assert
        assertFalse(added);
        assertEquals(1, hotel.getTotalRoomCount());
    }
    
    @Test
    @DisplayName("Should throw exception when adding null room")
    void testAddNullRoom() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> hotel.addRoom(null));
    }
    
    @Test
    @DisplayName("Should remove room successfully")
    void testRemoveRoom() {
        // Arrange
        Room room = new Room("101", RoomType.DOUBLE, 100.0);
        hotel.addRoom(room);
        
        // Act
        boolean removed = hotel.removeRoom("101");
        
        // Assert
        assertTrue(removed);
        assertEquals(0, hotel.getTotalRoomCount());
    }
    
    @Test
    @DisplayName("Should return false when removing non-existent room")
    void testRemoveNonExistentRoom() {
        // Act
        boolean removed = hotel.removeRoom("999");
        
        // Assert
        assertFalse(removed);
    }
    
    @Test
    @DisplayName("Should get room by number")
    void testGetRoomByNumber() {
        // Arrange
        Room room = new Room("101", RoomType.DOUBLE, 100.0);
        hotel.addRoom(room);
        
        // Act
        Room retrieved = hotel.getRoomByNumber("101");
        
        // Assert
        assertNotNull(retrieved);
        assertEquals("101", retrieved.getRoomNumber());
    }
    
    @Test
    @DisplayName("Should return null for non-existent room")
    void testGetNonExistentRoom() {
        // Act
        Room retrieved = hotel.getRoomByNumber("999");
        
        // Assert
        assertNull(retrieved);
    }
    
    @Test
    @DisplayName("Should get all rooms")
    void testGetAllRooms() {
        // Arrange
        hotel.addRoom(new Room("101", RoomType.DOUBLE, 100.0));
        hotel.addRoom(new Room("102", RoomType.SINGLE, 75.0));
        hotel.addRoom(new Room("103", RoomType.SUITE, 200.0));
        
        // Act
        var allRooms = hotel.getAllRooms();
        
        // Assert
        assertEquals(3, allRooms.size());
    }
    
    @Test
    @DisplayName("Should get available rooms only")
    void testGetAvailableRooms() {
        // Arrange
        Room room1 = new Room("101", RoomType.DOUBLE, 100.0);
        Room room2 = new Room("102", RoomType.SINGLE, 75.0);
        Room room3 = new Room("103", RoomType.SUITE, 200.0);
        
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);
        
        room2.setStatus(RoomStatus.OCCUPIED);
        
        // Act
        var availableRooms = hotel.getAvailableRooms();
        
        // Assert
        assertEquals(2, availableRooms.size());
        assertFalse(availableRooms.contains(room2));
    }
    
    @Test
    @DisplayName("Should count total rooms correctly")
    void testGetTotalRoomCount() {
        // Arrange
        for (int i = 1; i <= 5; i++) {
            hotel.addRoom(new Room(String.valueOf(i), RoomType.DOUBLE, 100.0));
        }
        
        // Act
        int count = hotel.getTotalRoomCount();
        
        // Assert
        assertEquals(5, count);
    }
    
    @Test
    @DisplayName("Should count available rooms correctly")
    void testGetAvailableRoomCount() {
        // Arrange
        Room room1 = new Room("101", RoomType.DOUBLE, 100.0);
        Room room2 = new Room("102", RoomType.SINGLE, 75.0);
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        room1.setStatus(RoomStatus.OCCUPIED);
        
        // Act
        int count = hotel.getAvailableRoomCount();
        
        // Assert
        assertEquals(1, count);
    }
    
    @Test
    @DisplayName("Should set star rating")
    void testSetStarRating() {
        // Act
        hotel.setStarRating(4);
        
        // Assert
        assertEquals(4, hotel.getStarRating());
    }
    
    @Test
    @DisplayName("Should consider equal hotels with same ID")
    void testEquality() {
        // Arrange
        Hotel hotel1 = new Hotel("H001", "Hotel A", "City A", 3);
        Hotel hotel2 = new Hotel("H001", "Hotel B", "City B", 5);
        
        // Act & Assert
        assertEquals(hotel1, hotel2);
    }
    
    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        // Arrange
        hotel.addRoom(new Room("101", RoomType.DOUBLE, 100.0));
        
        // Act
        String result = hotel.toString();
        
        // Assert
        assertTrue(result.contains("H001"));
        assertTrue(result.contains("Grand Hotel"));
    }
}
