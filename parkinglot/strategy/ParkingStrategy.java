package parkinglot.strategy;

import parkinglot.model.ParkingSpot;
import parkinglot.enums.VehicleType;
import java.util.List;

/**
 * Strategy Pattern - Different spot allocation strategies
 */
public interface ParkingStrategy {
    ParkingSpot findSpot(List<ParkingSpot> availableSpots, VehicleType vehicleType);
    String getDescription();
}
