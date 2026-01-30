package parkinglot;

import parkinglot.core.ParkingLot;
import parkinglot.factory.VehicleFactory;
import parkinglot.model.Vehicle;
import parkinglot.model.Ticket;
import parkinglot.strategy.*;

/**
 * Parking Lot Demo - Showcases all features for interview
 */
public class Demo {
    
    public static void main(String[] args) throws InterruptedException {
        ParkingLot lot = ParkingLot.getInstance();
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   PARKING LOT LLD Demo - Interview     ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // Initialize: 2 floors, 2 compact, 3 regular, 1 large per floor
        lot.initialize("City Mall Parking", 2, 2, 3, 1);
        
        // Demo 1: Park vehicles using Factory
        System.out.println("═══ Demo 1: Vehicle Factory & Parking ═══\n");
        
        Vehicle bike1 = VehicleFactory.createBike("BIKE-001");
        Vehicle car1 = VehicleFactory.createCar("CAR-001");
        Vehicle car2 = VehicleFactory.createCar("CAR-002");
        Vehicle truck1 = VehicleFactory.createTruck("TRUCK-001");
        
        Ticket t1 = lot.parkVehicle(bike1);
        Ticket t2 = lot.parkVehicle(car1);
        Ticket t3 = lot.parkVehicle(car2);
        Ticket t4 = lot.parkVehicle(truck1);
        
        lot.displayStatus();
        pause();
        
        // Demo 2: Strategy Pattern - Change parking strategy
        System.out.println("═══ Demo 2: Strategy Pattern - Parking ═══\n");
        System.out.println("Switching to NearElevator strategy...\n");
        lot.setParkingStrategy(new NearElevatorStrategy());
        
        Vehicle car3 = VehicleFactory.createCar("CAR-003");
        lot.parkVehicle(car3);
        pause();
        
        // Demo 3: Exit with Hourly Pricing
        System.out.println("\n═══ Demo 3: Vehicle Exit (Hourly Pricing) ═══\n");
        lot.exitVehicle("CAR-001");
        pause();
        
        // Demo 4: Change Pricing Strategy
        System.out.println("\n═══ Demo 4: Strategy Pattern - Pricing ═══\n");
        lot.setPricingStrategy(new DailyPricing());
        lot.exitVehicle(t4.getTicketId());
        pause();
        
        // Demo 5: Try to park duplicate vehicle
        System.out.println("\n═══ Demo 5: Duplicate Vehicle Prevention ═══\n");
        lot.parkVehicle(car2); // Should fail - already parked
        pause();
        
        // Demo 6: Fill up and show FULL
        System.out.println("\n═══ Demo 6: Fill Parking Lot ═══\n");
        for (int i = 4; i <= 15; i++) {
            lot.parkVehicle(VehicleFactory.createCar("CAR-" + String.format("%03d", i)));
        }
        
        lot.displayStatus();
        lot.displayActiveTickets();
        
        // Demo 7: Lot full scenario
        System.out.println("═══ Demo 7: Parking Full Scenario ═══\n");
        lot.parkVehicle(VehicleFactory.createCar("NEW-CAR"));
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          DEMO COMPLETE!                ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ ✓ Singleton Pattern (ParkingLot)       ║");
        System.out.println("║ ✓ Factory Pattern (VehicleFactory)     ║");
        System.out.println("║ ✓ Strategy: PricingStrategy            ║");
        System.out.println("║ ✓ Strategy: ParkingStrategy            ║");
        System.out.println("║ ✓ Multi-floor, Multi-vehicle-type      ║");
        System.out.println("║ ✓ Entry/Exit with Ticketing            ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    private static void pause() throws InterruptedException {
        Thread.sleep(300);
    }
}
