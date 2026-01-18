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
        assertThrows(IllegalArgumentException.class,
                () -> new BookingService(null));
    }

    @Test
    @DisplayName("Should create booking successfully")
    void testCreateBookingSuccess() throws Exception {
        Booking booking = bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        assertNotNull(booking);
        assertEquals("B001", booking.getBookingId());
        assertEquals("John Doe", booking.getGuestName());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(300.0, booking.getTotalPrice());
    }

    @Test
    @DisplayName("Should throw exception for booking in the past")
    void testCreateBookingInPast() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        LocalDate futureDate = pastDate.plusDays(3);
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", "John Doe", "101",
                        pastDate, futureDate));
    }

    @Test
    @DisplayName("Should throw exception for non-existent room")
    void testCreateBookingNonExistentRoom() {
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", "John Doe", "999",
                        checkInDate, checkOutDate));
    }

    @Test
    @DisplayName("Should throw exception when room not available")
    void testCreateBookingRoomNotAvailable() throws Exception {
        room.setStatus(RoomStatus.OCCUPIED);
        assertThrows(RoomNotAvailableException.class,
                () -> bookingService.createBooking("B001", "John Doe", "101",
                        checkInDate, checkOutDate));
    }

    @Test
    @DisplayName("Should throw exception for overlapping dates")
    void testCreateBookingOverlappingDates() throws Exception {
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = date1.plusDays(3);
        LocalDate date3 = date1.plusDays(1);
        LocalDate date4 = date2.plusDays(3);

        bookingService.createBooking("B001", "John Doe", "101", date1, date2);

        assertThrows(RoomNotAvailableException.class,
                () -> bookingService.createBooking("B002", "Jane Smith", "101",
                        date3, date4));
    }

    @Test
    @DisplayName("Should throw exception for null booking ID")
    void testCreateBookingNullId() {
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking(null, "John Doe", "101",
                        checkInDate, checkOutDate));
    }

    @Test
    @DisplayName("Should throw exception for null guest name")
    void testCreateBookingNullGuest() {
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", null, "101",
                        checkInDate, checkOutDate));
    }

    @Test
    @DisplayName("Should throw exception for null check-in date")
    void testCreateBookingNullCheckIn() {
        assertThrows(InvalidBookingException.class,
                () -> bookingService.createBooking("B001", "John Doe", "101",
                        null, checkOutDate));
    }

    @Test
    @DisplayName("Should check room availability correctly")
    void testIsRoomAvailableForDates() throws Exception {
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = date1.plusDays(3);
        LocalDate date3 = date2.plusDays(1);
        LocalDate date4 = date3.plusDays(3);

        bookingService.createBooking("B001", "John Doe", "101", date1, date2);

        boolean availableAfterBooking = bookingService.isRoomAvailableForDates("101", date3, date4);
        boolean notAvailableDuringBooking = bookingService.isRoomAvailableForDates("101", date1, date2);

        assertTrue(availableAfterBooking);
        assertFalse(notAvailableDuringBooking);
    }

    @Test
    @DisplayName("Should calculate total price correctly")
    void testCalculateTotalPrice() {
        double price = bookingService.calculateTotalPrice(room, checkInDate, checkOutDate);
        assertEquals(300.0, price);
    }

    @Test
    @DisplayName("Should cancel booking successfully")
    void testCancelBooking() throws Exception {
        Booking booking = bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        boolean cancelled = bookingService.cancelBooking("B001");
        assertTrue(cancelled);
        assertEquals(RoomStatus.AVAILABLE, room.getStatus());
    }

    @Test
    @DisplayName("Should return false when cancelling non-existent booking")
    void testCancelNonExistentBooking() {
        boolean cancelled = bookingService.cancelBooking("B999");
        assertFalse(cancelled);
    }

    @Test
    @DisplayName("Should get booking by ID")
    void testGetBookingById() throws Exception {
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        Booking retrieved = bookingService.getBookingById("B001");
        assertNotNull(retrieved);
        assertEquals("B001", retrieved.getBookingId());
    }

    @Test
    @DisplayName("Should return null for non-existent booking")
    void testGetNonExistentBooking() {
        Booking retrieved = bookingService.getBookingById("B999");
        assertNull(retrieved);
    }

    @Test
    @DisplayName("Should get bookings by guest")
    void testGetBookingsByGuest() throws Exception {
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);

        Room room2 = new Room("102", RoomType.SINGLE, 75.0);
        hotel.addRoom(room2);

        LocalDate date3 = checkOutDate.plusDays(1);
        LocalDate date4 = date3.plusDays(2);
        bookingService.createBooking("B002", "John Doe", "102", date3, date4);

        List<Booking> guestBookings = bookingService.getBookingsByGuest("John Doe");
        assertEquals(2, guestBookings.size());
    }

    @Test
    @DisplayName("Should get active bookings")
    void testGetActiveBookings() throws Exception {
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        List<Booking> activeBookings = bookingService.getActiveBookings();
        assertEquals(1, activeBookings.size());
    }

    @Test
    @DisplayName("Should get all bookings")
    void testGetAllBookings() throws Exception {
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        List<Booking> allBookings = bookingService.getAllBookings();
        assertEquals(1, allBookings.size());
    }

    @Test
    @DisplayName("Should get total bookings count")
    void testGetTotalBookings() throws Exception {
        bookingService.createBooking("B001", "John Doe", "101",
                checkInDate, checkOutDate);
        int count = bookingService.getTotalBookings();
        assertEquals(1, count);
    }
}
