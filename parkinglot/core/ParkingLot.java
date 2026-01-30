package parkinglot.core;

import parkinglot.enums.VehicleType;
import parkinglot.model.*;
import parkinglot.strategy.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton Pattern - Main Parking Lot controller
 */
public class ParkingLot {
    private static ParkingLot instance;
    
    private String name;
    private List<ParkingFloor> floors;
    private Map<Integer, Ticket> activeTickets;  // ticketId -> Ticket
    private Map<String, Ticket> vehicleTickets;  // licensePlate -> Ticket
    
    private PricingStrategy pricingStrategy;
    private ParkingStrategy parkingStrategy;
    
    private ParkingLot() {
        floors = new ArrayList<>();
        activeTickets = new HashMap<>();
        vehicleTickets = new HashMap<>();
        // Defaults
        pricingStrategy = new HourlyPricing();
        parkingStrategy = new NearEntranceStrategy();
    }
    
    public static ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }
    
    public static void resetInstance() {
        instance = null;
    }
    
    public void initialize(String name, int numFloors, int compactPerFloor, 
                          int regularPerFloor, int largePerFloor) {
        this.name = name;
        floors.clear();
        activeTickets.clear();
        vehicleTickets.clear();
        
        for (int i = 1; i <= numFloors; i++) {
            floors.add(new ParkingFloor(i, compactPerFloor, regularPerFloor, largePerFloor));
        }
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   " + name + " Initialized!");
        System.out.println("║   Floors: " + numFloors);
        System.out.println("║   Spots per floor: " + (compactPerFloor + regularPerFloor + largePerFloor));
        System.out.println("║   Pricing: " + pricingStrategy.getDescription());
        System.out.println("║   Allocation: " + parkingStrategy.getDescription());
        System.out.println("╚════════════════════════════════════════╝\n");
    }
    
    // Strategy setters
    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
        System.out.println("Pricing changed to: " + strategy.getDescription());
    }
    
    public void setParkingStrategy(ParkingStrategy strategy) {
        this.parkingStrategy = strategy;
        System.out.println("Allocation changed to: " + strategy.getDescription());
    }
    
    // Entry - Park a vehicle
    public Ticket parkVehicle(Vehicle vehicle) {
        if (vehicleTickets.containsKey(vehicle.getLicensePlate())) {
            System.out.println("ERROR: Vehicle " + vehicle.getLicensePlate() + " is already parked!");
            return null;
        }
        
        // Get all available spots across floors
        List<ParkingSpot> allAvailable = floors.stream()
            .flatMap(floor -> floor.getAvailableSpots().stream())
            .collect(Collectors.toList());
        
        if (allAvailable.isEmpty()) {
            System.out.println("ERROR: Parking lot is FULL!");
            return null;
        }
        
        // Use strategy to find best spot
        ParkingSpot spot = parkingStrategy.findSpot(allAvailable, vehicle.getType());
        
        if (spot == null) {
            System.out.println("ERROR: No suitable spot for " + vehicle.getType());
            return null;
        }
        
        // Park the vehicle
        spot.parkVehicle(vehicle);
        Ticket ticket = new Ticket(vehicle, spot);
        activeTickets.put(ticket.getTicketId(), ticket);
        vehicleTickets.put(vehicle.getLicensePlate(), ticket);
        
        System.out.println("✓ ENTRY: " + vehicle + " parked at " + spot);
        System.out.println("  " + ticket);
        
        return ticket;
    }
    
    // Exit - Remove vehicle and calculate payment
    public double exitVehicle(int ticketId) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            System.out.println("ERROR: Invalid ticket #" + ticketId);
            return -1;
        }
        
        return processExit(ticket);
    }
    
    public double exitVehicle(String licensePlate) {
        Ticket ticket = vehicleTickets.get(licensePlate);
        if (ticket == null) {
            System.out.println("ERROR: Vehicle " + licensePlate + " not found!");
            return -1;
        }
        
        return processExit(ticket);
    }
    
    private double processExit(Ticket ticket) {
        ticket.markExit();
        
        // Calculate payment using pricing strategy
        double amount = pricingStrategy.calculatePrice(ticket);
        ticket.setAmountPaid(amount);
        
        // Free up the spot
        ticket.getSpot().removeVehicle();
        
        // Remove from active tracking
        activeTickets.remove(ticket.getTicketId());
        vehicleTickets.remove(ticket.getVehicle().getLicensePlate());
        
        System.out.println("✓ EXIT: " + ticket.getVehicle());
        System.out.println("  Duration: " + ticket.getParkingDurationHours() + " hour(s)");
        System.out.println("  Amount: $" + String.format("%.2f", amount));
        
        return amount;
    }
    
    // Display status
    public void displayStatus() {
        System.out.println("\n═══════ " + name + " Status ═══════");
        int totalSpots = 0, available = 0;
        
        for (ParkingFloor floor : floors) {
            totalSpots += floor.getTotalSpots();
            available += floor.getAvailableCount();
            floor.displayStatus();
        }
        
        System.out.println("────────────────────────────────");
        System.out.printf("TOTAL: %d/%d spots available%n", available, totalSpots);
        System.out.println("Active vehicles: " + activeTickets.size());
        System.out.println("═════════════════════════════════\n");
    }
    
    public void displayActiveTickets() {
        System.out.println("\n═══════ Active Tickets ═══════");
        if (activeTickets.isEmpty()) {
            System.out.println("No vehicles parked.");
        } else {
            for (Ticket ticket : activeTickets.values()) {
                System.out.println("  " + ticket);
            }
        }
        System.out.println("══════════════════════════════\n");
    }
    
    // Getters
    public String getName() { return name; }
    public List<ParkingFloor> getFloors() { return floors; }
    public int getActiveVehicleCount() { return activeTickets.size(); }
}
