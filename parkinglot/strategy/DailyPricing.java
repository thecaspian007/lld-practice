package parkinglot.strategy;

import parkinglot.model.Ticket;
import parkinglot.enums.VehicleType;

/**
 * Daily flat rate pricing
 */
public class DailyPricing implements PricingStrategy {
    private static final double BIKE_DAILY = 50.0;
    private static final double CAR_DAILY = 100.0;
    private static final double TRUCK_DAILY = 150.0;
    
    @Override
    public double calculatePrice(Ticket ticket) {
        // Flat daily rate (better for long stays)
        return getDailyRate(ticket.getVehicle().getType());
    }
    
    private double getDailyRate(VehicleType type) {
        switch (type) {
            case BIKE: return BIKE_DAILY;
            case CAR: return CAR_DAILY;
            case TRUCK: return TRUCK_DAILY;
            default: return CAR_DAILY;
        }
    }
    
    @Override
    public String getDescription() {
        return String.format("Daily Flat: Bike=$%.0f, Car=$%.0f, Truck=$%.0f",
            BIKE_DAILY, CAR_DAILY, TRUCK_DAILY);
    }
}
