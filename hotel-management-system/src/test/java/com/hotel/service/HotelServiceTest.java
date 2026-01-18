package com.hotel.service;

import com.hotel.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HotelService Tests")
class HotelServiceTest {

    private HotelService hotelService;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel("H001", "Test Hotel", "Test City", 4);
        hotelService = new HotelService(hotel);

        hotel.addRoom(new Room("101", RoomType.SINGLE, 50.0));
        hotel.addRoom(new Room("102", RoomType.DOUBLE, 100.0));
        hotel.addRoom(new Room("103", RoomType.SUITE, 200.0));
        hotel.addRoom(new Room("104", RoomType.DELUXE, 150.0));
    }

    @Test
    @DisplayName("Should throw exception when hotel is null")
    void testServiceCreationWithNullHotel() {
        assertThrows(IllegalArgumentException.class,
                () -> new HotelService(null));
    }

    @Test
    @DisplayName("Should get hotel information")
    void testGetHotel() {
        Hotel retrieved = hotelService.getHotel();
        assertNotNull(retrieved);
        assertEquals("H001", retrieved.getHotelId());
        assertEquals("Test Hotel", retrieved.getHotelName());
    }

    @Test
    @DisplayName("Should check availability for date range")
    void testCheckAvailability() {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(3);
        List<Room> available = hotelService.checkAvailability(checkIn, checkOut);
        assertEquals(4, available.size());
    }

    @Test
    @DisplayName("Should throw exception for invalid date range")
    void testCheckAvailabilityInvalidDates() {
        LocalDate checkIn = LocalDate.now().plusDays(3);
        LocalDate checkOut = LocalDate.now().plusDays(1);
        assertThrows(IllegalArgumentException.class,
                () -> hotelService.checkAvailability(checkIn, checkOut));
    }

    @Test
    @DisplayName("Should get available rooms by type")
    void testGetAvailableRoomsByType() {
        List<Room> singleRooms = hotelService.getAvailableRoomsByType(RoomType.SINGLE);
        List<Room> doubleRooms = hotelService.getAvailableRoomsByType(RoomType.DOUBLE);
        assertEquals(1, singleRooms.size());
        assertEquals(1, doubleRooms.size());
        assertEquals(RoomType.SINGLE, singleRooms.get(0).getType());
    }

    @Test
    @DisplayName("Should get rooms by status")
    void testGetRoomsByStatus() {
        Room room = hotel.getRoomByNumber("101");
        room.setStatus(RoomStatus.OCCUPIED);
        List<Room> occupiedRooms = hotelService.getRoomsByStatus(RoomStatus.OCCUPIED);
        List<Room> availableRooms = hotelService.getRoomsByStatus(RoomStatus.AVAILABLE);
        assertEquals(1, occupiedRooms.size());
        assertEquals(3, availableRooms.size());
    }

    @Test
    @DisplayName("Should calculate average price of available rooms")
    void testGetAveragePriceOfAvailableRooms() {
        double average = hotelService.getAveragePriceOfAvailableRooms();
        assertEquals(125.0, average);
    }

    @Test
    @DisplayName("Should return 0 average when no available rooms")
    void testGetAveragePriceNoAvailableRooms() {
        for (Room room : hotel.getAllRooms()) {
            room.setStatus(RoomStatus.OCCUPIED);
        }
        double average = hotelService.getAveragePriceOfAvailableRooms();
        assertEquals(0.0, average);
    }

    @Test
    @DisplayName("Should find cheapest available room")
    void testFindCheapestAvailableRoom() {
        Room cheapest = hotelService.findCheapestAvailableRoom();
        assertNotNull(cheapest);
        assertEquals("101", cheapest.getRoomNumber());
        assertEquals(50.0, cheapest.getPricePerNight());
    }

    @Test
    @DisplayName("Should return null for cheapest when no available rooms")
    void testFindCheapestNoAvailableRooms() {
        for (Room room : hotel.getAllRooms()) {
            room.setStatus(RoomStatus.OCCUPIED);
        }
        Room cheapest = hotelService.findCheapestAvailableRoom();
        assertNull(cheapest);
    }

    @Test
    @DisplayName("Should find most expensive available room")
    void testFindMostExpensiveAvailableRoom() {
        Room expensive = hotelService.findMostExpensiveAvailableRoom();
        assertNotNull(expensive);
        assertEquals("103", expensive.getRoomNumber());
        assertEquals(200.0, expensive.getPricePerNight());
    }

    @Test
    @DisplayName("Should return null for most expensive when no available rooms")
    void testFindMostExpensiveNoAvailableRooms() {
        for (Room room : hotel.getAllRooms()) {
            room.setStatus(RoomStatus.OCCUPIED);
        }
        Room expensive = hotelService.findMostExpensiveAvailableRoom();
        assertNull(expensive);
    }

    @Test
    @DisplayName("Should calculate occupancy rate correctly")
    void testGetOccupancyRate() {
        hotel.getRoomByNumber("101").setStatus(RoomStatus.OCCUPIED);
        hotel.getRoomByNumber("102").setStatus(RoomStatus.OCCUPIED);
        double occupancy = hotelService.getOccupancyRate();
        assertEquals(50.0, occupancy);
    }

    @Test
    @DisplayName("Should return 0 occupancy rate for empty hotel")
    void testGetOccupancyRateEmpty() {
        Hotel emptyHotel = new Hotel("H002", "Empty Hotel", "City", 3);
        HotelService emptyService = new HotelService(emptyHotel);
        double occupancy = emptyService.getOccupancyRate();
        assertEquals(0.0, occupancy);
    }

    @Test
    @DisplayName("Should calculate full occupancy rate")
    void testGetOccupancyRateFull() {
        for (Room room : hotel.getAllRooms()) {
            room.setStatus(RoomStatus.OCCUPIED);
        }
        double occupancy = hotelService.getOccupancyRate();
        assertEquals(100.0, occupancy);
    }
}
