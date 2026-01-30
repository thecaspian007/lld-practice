package parkinglot.strategy;

import parkinglot.model.ParkingSpot;
import parkinglot.enums.VehicleType;
import java.util.List;
import java.util.Comparator;

/**
 * Find spot nearest to the elevator (better for multi-floor)
 */
public class NearElevatorStrategy implements ParkingStrategy {
    
    @Override
    public ParkingSpot findSpot(List<ParkingSpot> availableSpots, VehicleType vehicleType) {
        return availableSpots.stream()
            .filter(spot -> spot.canFitVehicle(vehicleType))
            .min(Comparator.comparingInt(ParkingSpot::getDistanceFromElevator))
            .orElse(null);
    }
    
    @Override
    public String getDescription() {
        return "Near Elevator - Allocates spot closest to elevator (multi-floor)";
    }
}
