# Hotel Management System

A comprehensive, enterprise-grade Object-Oriented hotel management system built with Java. This project demonstrates professional software construction practices including SOLID principles, clean code, defensive programming, and comprehensive testing.

## Overview

The Hotel Management System provides functionality for managing hotels, rooms, bookings, and availability checking. It showcases a complete hotel reservation workflow from hotel setup through room booking and reservation management.

## Language & Technology Stack

- **Language**: Java 11
- **Build Tool**: Apache Maven
- **Testing Framework**: JUnit 5 (Jupiter)
- **Design Patterns**: Factory, Service, Entity-Value Object patterns
- **Architecture**: Layered (Model-Service-Application)

## Project Structure

```
hotel-management-system/
├── pom.xml                                      # Maven configuration
├── README.md                                    # This file
└── src/
    ├── main/java/com/hotel/
    │   ├── model/                              # Domain model classes
    │   │   ├── Hotel.java                      # Hotel entity
    │   │   ├── Room.java                       # Room entity
    │   │   ├── Booking.java                    # Booking value object
    │   │   ├── RoomType.java                   # Enumeration
    │   │   ├── RoomStatus.java                 # Enumeration
    │   │   └── BookingStatus.java              # Enumeration
    │   ├── service/                             # Business logic services
    │   │   ├── HotelService.java               # Hotel operations
    │   │   └── BookingService.java             # Booking operations
    │   ├── exception/                           # Custom exceptions
    │   │   ├── HotelException.java             # Base exception
    │   │   ├── RoomNotAvailableException.java  # Room availability exception
    │   │   ├── InvalidBookingException.java    # Booking validation exception
    │   │   └── InvalidRoomException.java       # Room validation exception
    │   └── app/
    │       └── HotelManagementApp.java         # Main demonstration class
    └── test/java/com/hotel/
        ├── model/                              # Model tests
        │   ├── RoomTest.java
        │   ├── HotelTest.java
        │   └── BookingTest.java
        └── service/                            # Service tests
            ├── BookingServiceTest.java
            └── HotelServiceTest.java
```

## Key Features

### 1. Domain Model Classes

#### Hotel Class
- Hotel information management (ID, name, location, star rating)
- Room management (add, remove, retrieve)
- Availability tracking

#### Room Class
- Room identification and categorization
- Price management
- Status tracking (Available, Occupied, Maintenance, Reserved)
- Cost calculation

#### Booking Class (Immutable Value Object)
- Guest information
- Date range management
- Pricing information
- Status tracking

#### Enumerations
- **RoomType**: SINGLE, DOUBLE, SUITE, DELUXE
- **RoomStatus**: AVAILABLE, OCCUPIED, MAINTENANCE, RESERVED
- **BookingStatus**: CONFIRMED, CANCELLED, COMPLETED, PENDING

### 2. Service Layer

#### HotelService
- Availability checking for date ranges
- Room filtering by type and status
- Price analysis (average, cheapest, most expensive)
- Occupancy rate calculation

#### BookingService
- Booking creation with validation
- Date conflict detection
- Booking retrieval and filtering
- Booking cancellation

### 3. Exception Handling

Custom exception hierarchy for domain-specific errors:
- `HotelException` - Base exception
- `RoomNotAvailableException` - Room availability issues
- `InvalidBookingException` - Booking validation failures
- `InvalidRoomException` - Room validation failures

### 4. SOLID Principles Implementation

- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Extensible through service layer
- **Liskov Substitution**: Exception hierarchy follows substitution principle
- **Interface Segregation**: Focused public interfaces
- **Dependency Inversion**: Services depend on abstractions (implicit through composition)

### 5. Clean Code Practices

- Meaningful class, method, and variable names
- Small, focused methods (Single Responsibility)
- Comprehensive input validation
- Defensive programming with null checks
- Clear separation of concerns
- Immutable value objects where appropriate

## Comprehensive Test Suite

### Unit Tests

All classes include comprehensive unit tests following the **AAA Pattern** (Arrange, Act, Assert):

#### Model Tests
- **RoomTest.java** (23 tests)
  - Room creation and validation
  - Price calculation
  - Status management
  - Boundary and invalid cases
  
- **HotelTest.java** (20 tests)
  - Hotel creation and validation
  - Room management operations
  - Availability tracking
  - Room retrieval and filtering

- **BookingTest.java** (16 tests)
  - Booking creation with validation
  - Date range validation
  - Guest name validation
  - Price tracking

#### Service Tests
- **BookingServiceTest.java** (21 tests)
  - Booking creation with all scenarios
  - Date conflict detection
  - Booking cancellation
  - Booking queries (by guest, active bookings)
  - Error handling and exceptions

- **HotelServiceTest.java** (15 tests)
  - Availability checking
  - Room filtering by type and status
  - Price analysis
  - Occupancy rate calculation

### Test Characteristics

- **Parameterized Tests**: Using `@ParameterizedTest` for testing multiple values
- **Boundary Testing**: Testing edge cases (zero, negative, maximum values)
- **Invalid Cases**: Testing with null, empty, and invalid inputs
- **Normal Cases**: Testing expected happy path scenarios
- **AAA Pattern**: Clear Arrange, Act, Assert structure
- **Total Test Count**: 95+ test cases

## Running the Application

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build the Project

```bash
mvn clean install
```

### Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=RoomTest

# Run with coverage report
mvn clean test jacoco:report
# Coverage report: target/site/jacoco/index.html
```

### Run the Application

```bash
# Option 1: Using Maven
mvn exec:java -Dexec.mainClass="com.hotel.app.HotelManagementApp"

# Option 2: Run JAR file
mvn clean package
java -jar target/hotel-management-system-fat.jar
```

## Use Cases Demonstrated

The application demonstrates the following hotel management scenarios:

1. **Hotel Initialization**
   - Create hotel with basic information
   - Add multiple rooms of different types

2. **Room Management**
   - View all rooms
   - Check availability
   - Filter rooms by type or price
   - Track room status

3. **Booking Management**
   - Create bookings with date validation
   - Prevent overlapping bookings
   - Cancel bookings
   - Query bookings by guest

4. **Availability Checking**
   - Check available rooms for date ranges
   - Calculate costs for bookings
   - Find cheapest/most expensive rooms

5. **Business Analytics**
   - Calculate occupancy rates
   - Track revenue
   - Get average pricing

6. **Error Handling**
   - Prevent booking non-existent rooms
   - Prevent overlapping bookings
   - Validate all inputs
   - Clear error messages

## Sample Output

```
========================================
Hotel Management System Demo
========================================

1. INITIALIZING HOTEL
----------------------------------------
✓ Hotel created: Grand Luxury Hotel
✓ Total rooms: 7
✓ Available rooms: 7

2. HOTEL INFORMATION
----------------------------------------
Name: Grand Luxury Hotel
Location: New York
Star Rating: 5 stars
Total Rooms: 7
Average Price: $125.71

3. CHECKING ROOM AVAILABILITY
----------------------------------------
Check-in: 2026-01-24
Check-out: 2026-01-27
Number of nights: 3
Available rooms: 7
  - Room 101 (Single Room) - $79.99/night, Total: $239.97
  ...

5. CREATING BOOKINGS
----------------------------------------
✓ Booking 1 created:
  Guest: John Anderson
  Room: 201
  Check-in: 2026-01-12
  Check-out: 2026-01-14
  Total Price: $259.98
```

## Git Commit Plan

### Commit Strategy: Feature-based with atomic commits

1. **Initial Project Setup**
   ```
   feat: Initialize Maven project with structure and pom.xml
   - Create src/main/java and src/test/java directories
   - Configure pom.xml with JUnit 5 dependencies
   - Add Maven plugins for compilation and testing
   ```

2. **Domain Model Implementation**
   ```
   feat: Implement domain model classes and enumerations
   - Add Hotel, Room, Booking classes
   - Create RoomType, RoomStatus, BookingStatus enums
   - Implement value objects with defensive programming
   - Add comprehensive input validation and equals/hashCode
   ```

3. **Exception Handling & Services**
   ```
   feat: Add exception hierarchy and service layer
   - Create custom exception classes (HotelException, RoomNotAvailableException, etc.)
   - Implement HotelService for availability and analytics
   - Implement BookingService for booking operations
   - Add date conflict detection and validation
   ```

4. **Comprehensive Unit Tests**
   ```
   test: Add comprehensive unit tests for all classes
   - Write RoomTest with 23 test cases
   - Write HotelTest with 20 test cases
   - Write BookingTest with 16 test cases
   - Write BookingServiceTest with 21 test cases
   - Write HotelServiceTest with 15 test cases
   - All tests follow AAA pattern with parameterized tests
   ```

5. **Main Application & Documentation**
   ```
   feat: Add demonstration app and complete documentation
   - Implement HotelManagementApp with comprehensive demo
   - Create detailed README.md
   - Add usage examples and output demonstrations
   - Include project structure documentation
   ```

## Design Patterns Used

1. **Immutable Value Object Pattern**: Booking class
2. **Service Pattern**: HotelService, BookingService
3. **Repository Pattern** (implicit): Service classes act as repositories
4. **Validation Pattern**: Extensive input validation in constructors
5. **Exception Translation**: Domain-specific exceptions for error handling

## SOLID Principles Checklist

✓ **S**ingle Responsibility: Each class has one reason to change
✓ **O**pen/Closed: Services are open for extension, closed for modification
✓ **L**iskov Substitution: Exception hierarchy properly implements principle
✓ **I**nterface Segregation: Classes expose focused interfaces
✓ **D**ependency Inversion: High-level modules not dependent on low-level details

## Code Quality Metrics

- **Test Coverage Target**: >90%
- **Lines of Code**: ~1500 (including tests)
- **Number of Classes**: 13
- **Number of Test Cases**: 95+
- **Average Method Length**: <20 lines
- **Cyclomatic Complexity**: Low (mostly simple methods)

## Future Enhancements

1. Database persistence layer
2. REST API endpoints
3. User authentication and authorization
4. Payment processing integration
5. Email notifications
6. Room photos and amenities
7. Guest profiles and preferences
8. Special rates and discounts
9. Reporting and analytics dashboard
10. Concurrent booking management

## Best Practices Implemented

- ✓ Null-safe programming with Objects.requireNonNull()
- ✓ Immutable objects where appropriate
- ✓ Collections returned as unmodifiable
- ✓ Comprehensive error messages
- ✓ Input validation in all public methods
- ✓ Consistent naming conventions
- ✓ Clear separation of concerns
- ✓ No god classes
- ✓ Composition over inheritance
- ✓ Encapsulation of mutable state

## Running Tests with Coverage

```bash
mvn clean test jacoco:report
# Open target/site/jacoco/index.html in browser
```

## License

This project is part of Software Construction coursework. Free to use and modify for educational purposes.

## Author

Developed as a Software Construction CCP project demonstrating professional OOP design and clean code practices.

---

**Last Updated**: January 2026
**Java Version**: 11
**Maven Version**: 3.6+
**Status**: Complete and Production-Ready
