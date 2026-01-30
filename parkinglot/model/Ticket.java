package parkinglot.model;

import java.time.LocalDateTime;
import java.time.Duration;

public class Ticket {
    private static int counter = 0;
    
    private final int ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amountPaid;
    
    public Ticket(Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = ++counter;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
    }
    
    public void markExit() {
        this.exitTime = LocalDateTime.now();
    }
    
    public long getParkingDurationHours() {
        LocalDateTime end = (exitTime != null) ? exitTime : LocalDateTime.now();
        long minutes = Duration.between(entryTime, end).toMinutes();
        return Math.max(1, (minutes + 59) / 60); // Round up to nearest hour, min 1
    }
    
    // Getters
    public int getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public double getAmountPaid() { return amountPaid; }
    
    public void setAmountPaid(double amount) { this.amountPaid = amount; }
    
    @Override
    public String toString() {
        return String.format("Ticket#%d | %s | Spot: %s | Entry: %s", 
            ticketId, vehicle, spot, entryTime.toLocalTime());
    }
}
