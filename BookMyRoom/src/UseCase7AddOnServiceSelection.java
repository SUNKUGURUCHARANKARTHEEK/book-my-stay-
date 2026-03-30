import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates adding optional services to reservations using
 * Map<String, List<Service>> without modifying core booking logic.
 *

 */

// -------- SERVICE CLASS --------
class Service {
    String name;
    double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

// -------- ADD-ON SERVICE MANAGER --------
class AddOnServiceManager {
//
    // reservationId → list of services
    private HashMap<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added Service: " + service + " to Reservation: " + reservationId);
    }

    // Display services
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation: " + reservationId);

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println(s);
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.cost;
            }
        }

        return total;
    }
}

// -------- MAIN --------
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay - Add-On Services");
        System.out.println("=======================================");
        System.out.println("Version: 7.0\n");

        // Assume reservation already exists (from Use Case 6)
        String reservationId = "RES-101";

        // Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1200));
        manager.addService(reservationId, new Service("Extra Bed", 800));

        // Display services
        manager.displayServices(reservationId);
//
        // Total cost
        double total = manager.calculateTotalCost(reservationId);
        System.out.println("\nTotal Add-On Cost: ₹" + total);

        System.out.println("\n=======================================");
        System.out.println("Application Finished");
    }
}