import java.util.*;

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting using List.
 * Maintains confirmed reservations in insertion order and generates reports.
 *

 */

// -------- RESERVATION --------
class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// -------- BOOKING HISTORY --------
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// -------- REPORT SERVICE --------
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void displayAllBookings() {
        System.out.println("\n------ Booking History ------");

        List<Reservation> list = history.getAllReservations();

        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : list) {
            System.out.println(r);
        }

        System.out.println("-----------------------------");
    }

    // Generate summary report
    public void generateSummary() {
        System.out.println("\n------ Booking Summary ------");

        List<Reservation> list = history.getAllReservations();

        // Count bookings per room type
        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : list) {
            countMap.put(r.roomType,
                    countMap.getOrDefault(r.roomType, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " : " + countMap.get(type));
        }

        System.out.println("Total Bookings: " + list.size());
        System.out.println("-----------------------------");
    }
}

// -------- MAIN --------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Booking History");
        System.out.println("=======================================");
        System.out.println("Version: 8.0\n");

        // Booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        history.addReservation(new Reservation("RES-101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES-102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES-103", "Charlie", "Single Room"));
        history.addReservation(new Reservation("RES-104", "David", "Suite Room"));

        // Report service
        BookingReportService reportService = new BookingReportService(history);

        // Display history
        reportService.displayAllBookings();

        // Generate report
        reportService.generateSummary();

        System.out.println("\n=======================================");
        System.out.println("Application Finished");
    }
}