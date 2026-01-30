package parkinglot.model;

import parkinglot.enums.SpotType;
import parkinglot.enums.VehicleType;

public class ParkingSpot {
    private final int spotId;
    private final int floorNumber;
    private final SpotType spotType;
    private final int distanceFromEntrance;
    private final int distanceFromElevator;
    private Vehicle parkedVehicle;
    
    public ParkingSpot(int spotId, int floorNumber, SpotType spotType, 
                       int distanceFromEntrance, int distanceFromElevator) {
        this.spotId = spotId;
        this.floorNumber = floorNumber;
        this.spotType = spotType;
        this.distanceFromEntrance = distanceFromEntrance;
        this.distanceFromElevator = distanceFromElevator;
    }
    
    public boolean isAvailable() {
        return parkedVehicle == null;
    }
    
    public boolean canFitVehicle(VehicleType vehicleType) {
        // Check if spot size >= vehicle size requirement
        switch (vehicleType) {
            case BIKE: return true; // Bikes fit anywhere
            case CAR: return spotType == SpotType.REGULAR || spotType == SpotType.LARGE;
            case TRUCK: return spotType == SpotType.LARGE;
            default: return false;
        }
    }
    
    public boolean parkVehicle(Vehicle vehicle) {
        if (!isAvailable() || !canFitVehicle(vehicle.getType())) {
            return false;
        }
        this.parkedVehicle = vehicle;
        return true;
    }
    
    public Vehicle removeVehicle() {
        Vehicle vehicle = this.parkedVehicle;
        this.parkedVehicle = null;
        return vehicle;
    }
    
    // Getters
    public int getSpotId() { return spotId; }
    public int getFloorNumber() { return floorNumber; }
    public SpotType getSpotType() { return spotType; }
    public int getDistanceFromEntrance() { return distanceFromEntrance; }
    public int getDistanceFromElevator() { return distanceFromElevator; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
    
    @Override
    public String toString() {
        return String.format("F%d-S%d(%s)%s", floorNumber, spotId, 
            spotType.name().charAt(0), isAvailable() ? "" : "[OCCUPIED]");
    }
}
