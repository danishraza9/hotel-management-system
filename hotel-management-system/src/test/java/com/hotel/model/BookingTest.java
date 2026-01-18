package com.hotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Booking class.
 */
@DisplayName("Booking Tests")
class BookingTest {
    
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
    @BeforeEach
    void setUp() {
        room = new Room("101", RoomType.DOUBLE, 100.0);
        checkInDate = LocalDate.now().plusDays(1);
        checkOutDate = checkInDate.plusDays(3);
    }
    
    @Test
    @DisplayName("Should create booking with valid parameters")
    void testBookingCreation() {
        // Act
        Booking booking = new Booking("B001", "John Doe", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED);
        
        // Assert
        assertEquals("B001", booking.getBookingId());
        assertEquals("John Doe", booking.getGuestName());
        assertEquals(room, booking.getRoom());
        assertEquals(checkInDate, booking.getCheckInDate());
        assertEquals(checkOutDate, booking.getCheckOutDate());
        assertEquals(300.0, booking.getTotalPrice());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
    }
    
    @Test
    @DisplayName("Should throw exception for null booking ID")
    void testBookingIdNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking(null, "John Doe", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should throw exception for empty booking ID")
    void testBookingIdEmpty() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking("", "John Doe", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should throw exception for null guest name")
    void testGuestNameNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking("B001", null, room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should throw exception for short guest name")
    void testGuestNameTooShort() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking("B001", "J", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should throw exception for null room")
    void testRoomNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking("B001", "John Doe", null, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should throw exception for negative price")
    void testNegativePrice() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking("B001", "John Doe", room, checkInDate, checkOutDate, -100.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should throw exception when checkout is before checkin")
    void testCheckoutBeforeCheckin() {
        // Arrange
        LocalDate invalidCheckOut = checkInDate.minusDays(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking("B001", "John Doe", room, checkInDate, invalidCheckOut, 300.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should throw exception when checkout equals checkin")
    void testCheckoutEqualsCheckin() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Booking("B001", "John Doe", room, checkInDate, checkInDate, 300.0, BookingStatus.CONFIRMED));
    }
    
    @Test
    @DisplayName("Should calculate number of nights correctly")
    void testGetNumberOfNights() {
        // Arrange
        Booking booking = new Booking("B001", "John Doe", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED);
        
        // Act
        long nights = booking.getNumberOfNights();
        
        // Assert
        assertEquals(3, nights);
    }
    
    @Test
    @DisplayName("Should accept zero price")
    void testZeroPrice() {
        // Act
        Booking booking = new Booking("B001", "John Doe", room, checkInDate, checkOutDate, 0.0, BookingStatus.CONFIRMED);
        
        // Assert
        assertEquals(0.0, booking.getTotalPrice());
    }
    
    @Test
    @DisplayName("Should trim whitespace from booking ID")
    void testBookingIdTrimmed() {
        // Act
        Booking booking = new Booking("  B001  ", "John Doe", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED);
        
        // Assert
        assertEquals("B001", booking.getBookingId());
    }
    
    @Test
    @DisplayName("Should trim whitespace from guest name")
    void testGuestNameTrimmed() {
        // Act
        Booking booking = new Booking("B001", "  John Doe  ", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED);
        
        // Assert
        assertEquals("John Doe", booking.getGuestName());
    }
    
    @Test
    @DisplayName("Should consider bookings equal with same ID, guest, and date")
    void testEquality() {
        // Arrange
        Booking booking1 = new Booking("B001", "John Doe", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED);
        Booking booking2 = new Booking("B001", "John Doe", room, checkInDate, checkOutDate, 500.0, BookingStatus.CANCELLED);
        
        // Act & Assert
        assertEquals(booking1, booking2);
    }
    
    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        // Arrange
        Booking booking = new Booking("B001", "John Doe", room, checkInDate, checkOutDate, 300.0, BookingStatus.CONFIRMED);
        
        // Act
        String result = booking.toString();
        
        // Assert
        assertTrue(result.contains("B001"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("101"));
    }
}
