package parkinglot.strategy;

import parkinglot.model.Ticket;
import parkinglot.enums.VehicleType;

/**
 * Strategy Pattern - Different pricing strategies
 */
public interface PricingStrategy {
    double calculatePrice(Ticket ticket);
    String getDescription();
}
