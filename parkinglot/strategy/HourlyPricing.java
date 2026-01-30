package parkinglot.strategy;

import parkinglot.model.Ticket;
import parkinglot.enums.VehicleType;

/**
 * Hourly pricing: Rate per hour based on vehicle type
 */
public class HourlyPricing implements PricingStrategy {
    private static final double BIKE_RATE = 10.0;
    private static final double CAR_RATE = 20.0;
    private static final double TRUCK_RATE = 30.0;
    
    @Override
    public double calculatePrice(Ticket ticket) {
        long hours = ticket.getParkingDurationHours();
        double rate = getHourlyRate(ticket.getVehicle().getType());
        return hours * rate;
    }
    
    private double getHourlyRate(VehicleType type) {
        switch (type) {
            case BIKE: return BIKE_RATE;
            case CAR: return CAR_RATE;
            case TRUCK: return TRUCK_RATE;
            default: return CAR_RATE;
        }
    }
    
    @Override
    public String getDescription() {
        return String.format("Hourly: Bike=$%.0f/hr, Car=$%.0f/hr, Truck=$%.0f/hr",
            BIKE_RATE, CAR_RATE, TRUCK_RATE);
    }
}
