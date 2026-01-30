package parkinglot.model;

import parkinglot.enums.SpotType;
import parkinglot.enums.VehicleType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> spots;
    
    public ParkingFloor(int floorNumber, int compactSpots, int regularSpots, int largeSpots) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();
        initializeSpots(compactSpots, regularSpots, largeSpots);
    }
    
    private void initializeSpots(int compact, int regular, int large) {
        int spotId = 1;
        int distance = 1;
        
        // Add spots - closer spots have lower distance values
        for (int i = 0; i < compact; i++) {
            spots.add(new ParkingSpot(spotId++, floorNumber, SpotType.COMPACT, distance, distance + 5));
            distance++;
        }
        for (int i = 0; i < regular; i++) {
            spots.add(new ParkingSpot(spotId++, floorNumber, SpotType.REGULAR, distance, distance + 3));
            distance++;
        }
        for (int i = 0; i < large; i++) {
            spots.add(new ParkingSpot(spotId++, floorNumber, SpotType.LARGE, distance, distance + 2));
            distance++;
        }
    }
    
    public List<ParkingSpot> getAvailableSpots() {
        return spots.stream()
            .filter(ParkingSpot::isAvailable)
            .collect(Collectors.toList());
    }
    
    public List<ParkingSpot> getAvailableSpots(VehicleType type) {
        return spots.stream()
            .filter(ParkingSpot::isAvailable)
            .filter(spot -> spot.canFitVehicle(type))
            .collect(Collectors.toList());
    }
    
    public int getFloorNumber() { return floorNumber; }
    public List<ParkingSpot> getAllSpots() { return spots; }
    
    public int getTotalSpots() { return spots.size(); }
    public int getAvailableCount() { return (int) spots.stream().filter(ParkingSpot::isAvailable).count(); }
    
    public void displayStatus() {
        System.out.printf("Floor %d: %d/%d spots available%n", 
            floorNumber, getAvailableCount(), getTotalSpots());
        
        for (ParkingSpot spot : spots) {
            String status = spot.isAvailable() ? "[ ]" : "[X]";
            System.out.printf("  %s %s%n", status, spot);
        }
    }
}
