import java.io.*;
import java.util.*;

/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates persistence using serialization and recovery using deserialization.
 * Saves booking + inventory state to file and restores on restart.
 *

 */

// -------- RESERVATION --------
class Reservation implements Serializable {
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

// -------- SYSTEM STATE --------
class SystemState implements Serializable {
    List<Reservation> bookings;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookings, Map<String, Integer> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}
//
// -------- PERSISTENCE SERVICE --------
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // SAVE (Serialization)
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // LOAD (Deserialization)
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data. Starting safe state.");
        }
        return null;
    }
}

// -------- MAIN --------
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Persistence System");
        System.out.println("=======================================");
        System.out.println("Version: 12.0\n");

        // Try loading previous state
        SystemState state = PersistenceService.load();

        List<Reservation> bookings;
        Map<String, Integer> inventory;

        if (state != null) {
            // Restore state
            bookings = state.bookings;
            inventory = state.inventory;

            System.out.println("\n--- Restored Data ---");
            for (Reservation r : bookings) {
                System.out.println(r);
            }
            System.out.println("Inventory: " + inventory);

        } else {
            // Fresh start
            bookings = new ArrayList<>();
            inventory = new HashMap<>();

            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings.add(new Reservation("RES-101", "Alice", "Single Room"));
            bookings.add(new Reservation("RES-102", "Bob", "Double Room"));

            System.out.println("\n--- New Data Created ---");
        }

        // Display current state
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
        System.out.println("Inventory: " + inventory);

        // Save before exit
        SystemState newState = new SystemState(bookings, inventory);
        PersistenceService.save(newState);

        System.out.println("\n=======================================");
        System.out.println("Application Finished (State Saved)");
    }
}