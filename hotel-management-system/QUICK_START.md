# Quick Start Guide

## Hotel Management System - Get Started in 5 Minutes

### Prerequisites
- Java 11 or higher installed
- Maven 3.6 or higher installed
- Command line/terminal access

### Step 1: Navigate to Project
```bash
cd "d:\Last Backup\courses\Software Construction\CCP\hotel-management-system"
```

### Step 2: Build Project
```bash
mvn clean install
```
Expected output: `BUILD SUCCESS`

### Step 3: Run Tests
```bash
mvn test
```
Expected: 95+ tests passing

### Step 4: Run Application
```bash
mvn exec:java -Dexec.mainClass="com.hotel.app.HotelManagementApp"
```
Expected: Detailed console output with hotel management demo

### Step 5: View Coverage Report (Optional)
```bash
mvn clean test jacoco:report
# Then open: target/site/jacoco/index.html in browser
```

---

## What You'll See

The application demonstrates:

1. **Hotel Setup** - Creates 7-room luxury hotel
2. **Room Availability** - Checks available rooms for dates
3. **Price Analysis** - Shows pricing statistics
4. **Bookings** - Creates 3 sample bookings
5. **Queries** - Retrieves bookings by guest
6. **Error Handling** - Shows exception handling
7. **Statistics** - Displays occupancy and revenue

---

## Project Files

**Main Classes** (src/main/java/com/hotel/):
- `model/` - Hotel, Room, Booking entities
- `service/` - HotelService, BookingService
- `exception/` - Custom exceptions
- `app/` - HotelManagementApp (Main class)

**Test Classes** (src/test/java/com/hotel/):
- `model/` - RoomTest, HotelTest, BookingTest
- `service/` - BookingServiceTest, HotelServiceTest

**Configuration**:
- `pom.xml` - Maven configuration
- `README.md` - Full documentation
- `.gitignore` - Git ignore patterns

---

## Key Features

✓ **13 Classes**: Hotel, Room, Booking, Services, Exceptions
✓ **95+ Tests**: Comprehensive JUnit 5 test suite
✓ **SOLID Principles**: All 5 principles implemented
✓ **Clean Code**: Professional practices throughout
✓ **Defensive Programming**: Extensive input validation
✓ **Exception Handling**: Custom exception hierarchy
✓ **Immutable Objects**: Booking as value object
✓ **Date Handling**: LocalDate with conflict detection

---

## Test Results Expected

```
Tests run: 95
Failures: 0
Errors: 0
Skipped: 0
Success: 100%

Coverage:
- Lines: >90%
- Branches: >85%
- Methods: >95%
```

---

## Common Commands

```bash
# Build
mvn clean install

# Test all
mvn test

# Test specific class
mvn test -Dtest=RoomTest

# Run app
mvn exec:java -Dexec.mainClass="com.hotel.app.HotelManagementApp"

# Create JAR
mvn clean package

# Run JAR
java -jar target/hotel-management-system-fat.jar

# Coverage report
mvn clean test jacoco:report

# Clean build
mvn clean
```

---

## Project Structure

```
hotel-management-system/
├── src/main/java/com/hotel/
│   ├── model/          (6 classes)
│   ├── service/        (2 classes)
│   ├── exception/      (4 classes)
│   └── app/           (1 class)
├── src/test/java/com/hotel/
│   ├── model/         (3 test classes)
│   └── service/       (2 test classes)
├── pom.xml
└── README.md
```

---

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

5. CREATING BOOKINGS
----------------------------------------
✓ Booking 1 created:
  Guest: John Anderson
  Room: 201
  Check-in: 2026-01-12
  Check-out: 2026-01-14
  Total Price: $259.98

8. FINAL STATISTICS
----------------------------------------
Hotel Status:
  Total rooms: 7
  Available rooms: 4
  Occupancy rate: 42.9%

Booking Summary:
  Total bookings: 3
  Total revenue: $809.94
```

---

## Troubleshooting

### Issue: "java: command not found"
**Solution**: Install Java 11+. Check: `java -version`

### Issue: "mvn: command not found"
**Solution**: Install Maven 3.6+. Check: `mvn -version`

### Issue: Tests fail
**Solution**: Run `mvn clean install` first to download dependencies

### Issue: Port already in use
**Solution**: App doesn't use ports. Check process is running correctly.

---

## Documentation Files

- **README.md** - Complete feature documentation
- **PROJECT_SUMMARY.md** - Detailed architecture and design
- **DELIVERABLES.md** - Complete deliverables checklist
- **QUICK_START.md** - This file

---

## Next Steps

1. ✓ Build project: `mvn clean install`
2. ✓ Run tests: `mvn test`
3. ✓ Run app: `mvn exec:java -Dexec.mainClass="com.hotel.app.HotelManagementApp"`
4. ✓ Review code in `src/main/java/com/hotel/`
5. ✓ Check tests in `src/test/java/com/hotel/`
6. ✓ Read README.md for full documentation

---

## Performance

- **Build Time**: ~10-15 seconds
- **Test Time**: ~5-10 seconds
- **App Execution**: ~2-3 seconds
- **Jar Size**: ~30 KB (fat JAR ~10 MB with dependencies)

---

## Java Compatibility

- Minimum: Java 11
- Target: Java 11+
- Tested: Java 11, 17
- Features: LocalDate, Streams, Optional

---

## Support Files

All support files included:
- ✓ pom.xml - Maven configuration
- ✓ .gitignore - Git ignore patterns
- ✓ README.md - Full documentation
- ✓ PROJECT_SUMMARY.md - Architecture details
- ✓ DELIVERABLES.md - Checklist
- ✓ QUICK_START.md - This guide

---

**Ready to go!** Start with Step 1 above.

Questions? Check README.md or DELIVERABLES.md for detailed information.
