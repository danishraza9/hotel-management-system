package com.hotel.service;

import com.hotel.exception.InvalidBookingException;
import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BookingService class.
 */
@DisplayName("BookingService Tests")
class BookingServiceTest {
    
    private BookingService bookingService;
    private Hotel hotel;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
    @BeforeEach
    void setUp() {
        hotel = new Hotel("H001", "Test Hotel", "Test City", 4);
        room = new Room("101", RoomType.DOUBLE, 100.0);
        hotel.addRoom(room);
        
        bookingService = new BookingService(hotel);
        checkInDate = LocalDate.now().plusDays(1);
        checkOutDate = checkInDate.plusDays(3);
    }
    
    @Test
    @DisplayName("Should throw exception when hotel is null")
    void testServiceCreationWithNullHotel() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new BookingService(null));
    }
    
    @Test
    @DisplayName("Should create booking successfully")
    void testCreateBookingSuccess() throws Exception {
        // Act
        Booking booking = bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        
        // Assert
        assertNotNull(booking);
        assertEquals("B001", booking.getBookingId());
        assertEquals("John Doe", booking.getGuestName());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(300.0, booking.getTotalPrice());
    }
    
    @Test
    @DisplayName("Should throw exception for booking in the past")
    void testCreateBookingInPast() {
        // Arrange
        LocalDate pastDate = LocalDate.now().minusDays(1);
        LocalDate futureDate = pastDate.plusDays(3);
        
        // Act & Assert
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", "John Doe", "101",
                        pastDate, futureDate));
    }
    
    @Test
    @DisplayName("Should throw exception for non-existent room")
    void testCreateBookingNonExistentRoom() {
        // Act & Assert
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", "John Doe", "999",
                        checkInDate, checkOutDate));
    }
    
    @Test
    @DisplayName("Should throw exception when room not available")
    void testCreateBookingRoomNotAvailable() throws Exception {
        // Arrange
        room.setStatus(RoomStatus.OCCUPIED);
        
        // Act & Assert
        assertThrows(RoomNotAvailableException.class,
                () -> bookingService.createBooking("B001", "John Doe", "101",
                        checkInDate, checkOutDate));
    }
    
    @Test
    @DisplayName("Should throw exception for overlapping dates")
    void testCreateBookingOverlappingDates() throws Exception {
        // Arrange
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = date1.plusDays(3);
        LocalDate date3 = date1.plusDays(1);
        LocalDate date4 = date2.plusDays(3);
        
        bookingService.createBooking("B001", "John Doe", "101", date1, date2);
        
        // Act & Assert
        assertThrows(RoomNotAvailableException.class,
                () -> bookingService.createBooking("B002", "Jane Smith", "101",
                        date3, date4));
    }
    
    @Test
    @DisplayName("Should throw exception for null booking ID")
    void testCreateBookingNullId() {
        // Act & Assert
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking(null, "John Doe", "101",
                        checkInDate, checkOutDate));
    }
    
    @Test
    @DisplayName("Should throw exception for null guest name")
    void testCreateBookingNullGuest() {
        // Act & Assert
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", null, "101",
                        checkInDate, checkOutDate));
    }
    
    @Test
    @DisplayName("Should throw exception for null check-in date")
    void testCreateBookingNullCheckIn() {
        // Act & Assert
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", "John Doe", "101",
                        null, checkOutDate));
    }
    
    @Test
    @DisplayName("Should check room availability correctly")
    void testIsRoomAvailableForDates() throws Exception {
        // Arrange
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = date1.plusDays(3);
        LocalDate date3 = date2.plusDays(1);
        LocalDate date4 = date3.plusDays(3);
        
        bookingService.createBooking("B001", "John Doe", "101", date1, date2);
        
        // Act
        boolean availableAfterBooking = bookingService.isRoomAvailableForDates("101", date3, date4);
        boolean notAvailableDuringBooking = bookingService.isRoomAvailableForDates("101", date1, date2);
        
        // Assert
        assertTrue(availableAfterBooking);
        assertFalse(notAvailableDuringBooking);
    }
    
    @Test
    @DisplayName("Should calculate total price correctly")
    void testCalculateTotalPrice() {
        // Act
        double price = bookingService.calculateTotalPrice(room, checkInDate, checkOutDate);
        
        // Assert
        assertEquals(300.0, price); // 100 per night * 3 nights
    }
    
    @Test
    @DisplayName("Should cancel booking successfully")
    void testCancelBooking() throws Exception {
        // Arrange
        Booking booking = bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        
        // Act
        boolean cancelled = bookingService.cancelBooking("B001");
        
        // Assert
        assertTrue(cancelled);
        assertEquals(RoomStatus.AVAILABLE, room.getStatus());
    }
    
    @Test
    @DisplayName("Should return false when cancelling non-existent booking")
    void testCancelNonExistentBooking() {
        // Act
        boolean cancelled = bookingService.cancelBooking("B999");
        
        // Assert
        assertFalse(cancelled);
    }
    
    @Test
    @DisplayName("Should get booking by ID")
    void testGetBookingById() throws Exception {
        // Arrange
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        
        // Act
        Booking retrieved = bookingService.getBookingById("B001");
        
        // Assert
        assertNotNull(retrieved);
        assertEquals("B001", retrieved.getBookingId());
    }
    
    @Test
    @DisplayName("Should return null for non-existent booking")
    void testGetNonExistentBooking() {
        // Act
        Booking retrieved = bookingService.getBookingById("B999");
        
        // Assert
        assertNull(retrieved);
    }
    
    @Test
    @DisplayName("Should get bookings by guest")
    void testGetBookingsByGuest() throws Exception {
        // Arrange
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        
        Room room2 = new Room("102", RoomType.SINGLE, 75.0);
        hotel.addRoom(room2);
        
        LocalDate date3 = checkOutDate.plusDays(1);
        LocalDate date4 = date3.plusDays(2);
        bookingService.createBooking("B002", "John Doe", "102", date3, date4);
        
        // Act
        List<Booking> guestBookings = bookingService.getBookingsByGuest("John Doe");
        
        // Assert
        assertEquals(2, guestBookings.size());
    }
    
    @Test
    @DisplayName("Should get active bookings")
    void testGetActiveBookings() throws Exception {
        // Arrange
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        
        // Act
        List<Booking> activeBookings = bookingService.getActiveBookings();
        
        // Assert
        assertEquals(1, activeBookings.size());
    }
    
    @Test
    @DisplayName("Should get all bookings")
    void testGetAllBookings() throws Exception {
        // Arrange
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        
        // Act
        List<Booking> allBookings = bookingService.getAllBookings();
        
        // Assert
        assertEquals(1, allBookings.size());
    }
    
    @Test
    @DisplayName("Should get total bookings count")
    void testGetTotalBookings() throws Exception {
        // Arrange
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        
        // Act
        int count = bookingService.getTotalBookings();
        
        // Assert
        assertEquals(1, count);
    }
}
