package parkinglot.strategy;

import parkinglot.model.ParkingSpot;
import parkinglot.enums.VehicleType;
import java.util.List;
import java.util.Comparator;

/**
 * Find spot nearest to the entrance
 */
public class NearEntranceStrategy implements ParkingStrategy {
    
    @Override
    public ParkingSpot findSpot(List<ParkingSpot> availableSpots, VehicleType vehicleType) {
        return availableSpots.stream()
            .filter(spot -> spot.canFitVehicle(vehicleType))
            .min(Comparator.comparingInt(ParkingSpot::getDistanceFromEntrance))
            .orElse(null);
    }
    
    @Override
    public String getDescription() {
        return "Near Entrance - Allocates spot closest to parking lot entrance";
    }
}
