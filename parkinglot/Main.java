package parkinglot;

import parkinglot.core.ParkingLot;
import parkinglot.factory.VehicleFactory;
import parkinglot.model.Vehicle;
import parkinglot.strategy.*;
import java.util.Scanner;

/**
 * Interactive Parking Lot Console
 */
public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot lot = ParkingLot.getInstance();
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      PARKING LOT - LLD Interview       ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // Initialize with default config
        lot.initialize("City Parking", 2, 2, 3, 1);
        
        printHelp();
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");
            
            if (parts.length == 0 || parts[0].isEmpty()) continue;
            
            String command = parts[0].toLowerCase();
            
            switch (command) {
                case "park":
                    if (parts.length < 3) {
                        System.out.println("Usage: park <type> <license>");
                        break;
                    }
                    try {
                        Vehicle v = VehicleFactory.createVehicle(parts[2], parts[1]);
                        lot.parkVehicle(v);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                    
                case "exit":
                    if (parts.length < 2) {
                        System.out.println("Usage: exit <license>");
                        break;
                    }
                    lot.exitVehicle(parts[1]);
                    break;
                    
                case "status":
                    lot.displayStatus();
                    break;
                    
                case "tickets":
                    lot.displayActiveTickets();
                    break;
                    
                case "pricing":
                    if (parts.length < 2) {
                        System.out.println("Usage: pricing <hourly|daily>");
                        break;
                    }
                    if (parts[1].equalsIgnoreCase("hourly")) {
                        lot.setPricingStrategy(new HourlyPricing());
                    } else if (parts[1].equalsIgnoreCase("daily")) {
                        lot.setPricingStrategy(new DailyPricing());
                    }
                    break;
                    
                case "strategy":
                    if (parts.length < 2) {
                        System.out.println("Usage: strategy <entrance|elevator>");
                        break;
                    }
                    if (parts[1].equalsIgnoreCase("entrance")) {
                        lot.setParkingStrategy(new NearEntranceStrategy());
                    } else if (parts[1].equalsIgnoreCase("elevator")) {
                        lot.setParkingStrategy(new NearElevatorStrategy());
                    }
                    break;
                    
                case "help":
                case "h":
                    printHelp();
                    break;
                    
                case "quit":
                case "q":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Unknown command. Type 'help' for commands.");
            }
        }
    }
    
    private static void printHelp() {
        System.out.println("\nCommands:");
        System.out.println("  park <bike|car|truck> <license>  - Park a vehicle");
        System.out.println("  exit <license>                   - Vehicle exit");
        System.out.println("  status                           - Show all spots");
        System.out.println("  tickets                          - Show active tickets");
        System.out.println("  pricing <hourly|daily>           - Change pricing");
        System.out.println("  strategy <entrance|elevator>     - Change allocation");
        System.out.println("  help                             - Show this help");
        System.out.println("  quit                             - Exit\n");
    }
}
