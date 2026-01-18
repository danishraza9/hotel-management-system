package com.hotel.app;

import com.hotel.exception.InvalidBookingException;
import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.*;
import com.hotel.service.BookingService;
import com.hotel.service.HotelService;

import java.time.LocalDate;
import java.util.List;

/**
 * Main application class demonstrating hotel management system functionality.
 * Shows all major use cases including hotel setup, room booking, availability checking.
 */
public class HotelManagementApp {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Hotel Management System Demo");
        System.out.println("========================================\n");
        
        try {
            // Initialize hotel with rooms
            Hotel hotel = initializeHotel();
            
            // Create services
            HotelService hotelService = new HotelService(hotel);
            BookingService bookingService = new BookingService(hotel);
            
            // Display hotel information
            displayHotelInfo(hotel, hotelService);
            
            // Demonstrate availability checking
            demonstrateAvailabilityCheck(hotelService);
            
            // Demonstrate room filtering
            demonstrateRoomFiltering(hotelService);
            
            // Demonstrate booking creation
            demonstrateBookingCreation(bookingService);
            
            // Demonstrate booking queries
            demonstrateBookingQueries(bookingService);
            
            // Demonstrate error handling
            demonstrateErrorHandling(bookingService);
            
            // Display final statistics
            displayFinalStatistics(hotel, hotelService, bookingService);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes a hotel with multiple rooms of different types.
     */
    private static Hotel initializeHotel() {
        System.out.println("1. INITIALIZING HOTEL");
        System.out.println("-".repeat(40));
        
        Hotel hotel = new Hotel("LUXURY-001", "Grand Luxury Hotel", "New York", 5);
        
        // Add rooms
        hotel.addRoom(new Room("101", RoomType.SINGLE, 79.99));
        hotel.addRoom(new Room("102", RoomType.SINGLE, 79.99));
        hotel.addRoom(new Room("201", RoomType.DOUBLE, 129.99));
        hotel.addRoom(new Room("202", RoomType.DOUBLE, 129.99));
        hotel.addRoom(new Room("203", RoomType.DOUBLE, 129.99));
        hotel.addRoom(new Room("301", RoomType.SUITE, 199.99));
        hotel.addRoom(new Room("302", RoomType.DELUXE, 159.99));
        
        System.out.println("✓ Hotel created: " + hotel.getHotelName());
        System.out.println("✓ Total rooms: " + hotel.getTotalRoomCount());
        System.out.println("✓ Available rooms: " + hotel.getAvailableRoomCount());
        System.out.println();
        
        return hotel;
    }
    
    /**
     * Displays hotel information and statistics.
     */
    private static void displayHotelInfo(Hotel hotel, HotelService hotelService) {
        System.out.println("2. HOTEL INFORMATION");
        System.out.println("-".repeat(40));
        System.out.println("Name: " + hotel.getHotelName());
        System.out.println("Location: " + hotel.getLocation());
        System.out.println("Star Rating: " + hotel.getStarRating() + " stars");
        System.out.println("Total Rooms: " + hotel.getTotalRoomCount());
        System.out.println("Average Price: $" + String.format("%.2f", hotelService.getAveragePriceOfAvailableRooms()));
        System.out.println();
    }
    
    /**
     * Demonstrates room availability checking.
     */
    private static void demonstrateAvailabilityCheck(HotelService hotelService) {
        System.out.println("3. CHECKING ROOM AVAILABILITY");
        System.out.println("-".repeat(40));
        
        LocalDate checkIn = LocalDate.now().plusDays(5);
        LocalDate checkOut = checkIn.plusDays(3);
        
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        System.out.println("Number of nights: " + java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut));
        
        List<Room> available = hotelService.checkAvailability(checkIn, checkOut);
        System.out.println("Available rooms: " + available.size());
        
        for (Room room : available) {
            double cost = room.calculateTotalCost(
                    java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut)
            );
            System.out.println("  - Room " + room.getRoomNumber() + " (" + room.getType().getDisplayName() +
                    ") - $" + String.format("%.2f", room.getPricePerNight()) +
                    "/night, Total: $" + String.format("%.2f", cost));
        }
        System.out.println();
    }
    
    /**
     * Demonstrates filtering rooms by type and status.
     */
    private static void demonstrateRoomFiltering(HotelService hotelService) {
        System.out.println("4. ROOM FILTERING");
        System.out.println("-".repeat(40));
        
        // Filter by type
        System.out.println("Double Rooms:");
        List<Room> doubleRooms = hotelService.getAvailableRoomsByType(RoomType.DOUBLE);
        for (Room room : doubleRooms) {
            System.out.println("  - Room " + room.getRoomNumber() + ": $" + 
                    String.format("%.2f", room.getPricePerNight()));
        }
        
        // Show price range
        Room cheapest = hotelService.findCheapestAvailableRoom();
        Room expensive = hotelService.findMostExpensiveAvailableRoom();
        System.out.println("Price range: $" + String.format("%.2f", cheapest.getPricePerNight()) +
                " - $" + String.format("%.2f", expensive.getPricePerNight()));
        System.out.println();
    }
    
    /**
     * Demonstrates booking creation with multiple scenarios.
     */
    private static void demonstrateBookingCreation(BookingService bookingService) {
        System.out.println("5. CREATING BOOKINGS");
        System.out.println("-".repeat(40));
        
        LocalDate checkIn1 = LocalDate.now().plusDays(7);
        LocalDate checkOut1 = checkIn1.plusDays(2);
        
        LocalDate checkIn2 = LocalDate.now().plusDays(10);
        LocalDate checkOut2 = checkIn2.plusDays(1);
        
        try {
            // Booking 1
            Booking booking1 = bookingService.createBooking("BK001", "John Anderson", "201",
                    checkIn1, checkOut1);
            System.out.println("✓ Booking 1 created:");
            System.out.println("  Guest: " + booking1.getGuestName());
            System.out.println("  Room: " + booking1.getRoom().getRoomNumber());
            System.out.println("  Check-in: " + booking1.getCheckInDate());
            System.out.println("  Check-out: " + booking1.getCheckOutDate());
            System.out.println("  Total Price: $" + String.format("%.2f", booking1.getTotalPrice()));
            System.out.println();
            
            // Booking 2
            Booking booking2 = bookingService.createBooking("BK002", "Emma Wilson", "301",
                    checkIn2, checkOut2);
            System.out.println("✓ Booking 2 created:");
            System.out.println("  Guest: " + booking2.getGuestName());
            System.out.println("  Room: " + booking2.getRoom().getRoomNumber());
            System.out.println("  Total Price: $" + String.format("%.2f", booking2.getTotalPrice()));
            System.out.println();
            
            // Booking 3 - Different dates, same room as booking 1
            LocalDate checkIn3 = checkOut1.plusDays(1);
            LocalDate checkOut3 = checkIn3.plusDays(3);
            
            Booking booking3 = bookingService.createBooking("BK003", "Michael Brown", "201",
                    checkIn3, checkOut3);
            System.out.println("✓ Booking 3 created (same room, different dates):");
            System.out.println("  Guest: " + booking3.getGuestName());
            System.out.println("  Room: " + booking3.getRoom().getRoomNumber());
            System.out.println("  Check-in: " + booking3.getCheckInDate());
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates various booking queries.
     */
    private static void demonstrateBookingQueries(BookingService bookingService) {
        System.out.println("6. BOOKING QUERIES");
        System.out.println("-".repeat(40));
        
        // Get all bookings
        System.out.println("Total bookings: " + bookingService.getTotalBookings());
        
        // Get bookings by guest
        System.out.println("\nBookings by guest:");
        List<Booking> johnBookings = bookingService.getBookingsByGuest("John Anderson");
        System.out.println("  John Anderson: " + johnBookings.size() + " booking(s)");
        
        // Get active bookings
        System.out.println("\nActive bookings: " + bookingService.getActiveBookings().size());
        System.out.println();
    }
    
    /**
     * Demonstrates error handling for various invalid scenarios.
     */
    private static void demonstrateErrorHandling(BookingService bookingService) {
        System.out.println("7. ERROR HANDLING & VALIDATION");
        System.out.println("-".repeat(40));
        
        LocalDate checkIn = LocalDate.now().plusDays(15);
        LocalDate checkOut = checkIn.plusDays(2);
        
        // Try to book already booked room
        try {
            System.out.println("Attempting to book already reserved room...");
            bookingService.createBooking("BK999", "Test Guest", "201", checkIn, checkOut);
            System.out.println("✗ Should have failed!");
        } catch (RoomNotAvailableException e) {
            System.out.println("✓ Correctly caught exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✓ Error: " + e.getMessage());
        }
        
        // Try to book with past date
        try {
            System.out.println("\nAttempting to book with past check-in date...");
            LocalDate pastDate = LocalDate.now().minusDays(1);
            bookingService.createBooking("BK999", "Test Guest", "102", 
                    pastDate, pastDate.plusDays(2));
            System.out.println("✗ Should have failed!");
        } catch (InvalidBookingException e) {
            System.out.println("✓ Correctly caught exception: " + e.getMessage());
        }
        
        // Try invalid date range
        try {
            System.out.println("\nAttempting booking with invalid date range...");
            LocalDate date1 = LocalDate.now().plusDays(20);
            bookingService.createBooking("BK999", "Test Guest", "102", 
                    date1, date1.minusDays(1));
            System.out.println("✗ Should have failed!");
        } catch (InvalidBookingException e) {
            System.out.println("✓ Correctly caught exception: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Displays final statistics and summary.
     */
    private static void displayFinalStatistics(Hotel hotel, HotelService hotelService, 
                                               BookingService bookingService) {
        System.out.println("8. FINAL STATISTICS");
        System.out.println("-".repeat(40));
        
        System.out.println("Hotel Status:");
        System.out.println("  Total rooms: " + hotel.getTotalRoomCount());
        System.out.println("  Available rooms: " + hotel.getAvailableRoomCount());
        System.out.println("  Occupancy rate: " + String.format("%.1f", hotelService.getOccupancyRate()) + "%");
        
        System.out.println("\nBooking Summary:");
        System.out.println("  Total bookings: " + bookingService.getTotalBookings());
        System.out.println("  Active bookings: " + bookingService.getActiveBookings().size());
        
        List<Booking> allBookings = bookingService.getAllBookings();
        if (!allBookings.isEmpty()) {
            double totalRevenue = allBookings.stream()
                    .mapToDouble(Booking::getTotalPrice)
                    .sum();
            System.out.println("  Total revenue: $" + String.format("%.2f", totalRevenue));
            System.out.println("  Average booking value: $" + 
                    String.format("%.2f", totalRevenue / allBookings.size()));
        }
        
        System.out.println("\n========================================");
        System.out.println("Demo completed successfully!");
        System.out.println("========================================");
    }
}
