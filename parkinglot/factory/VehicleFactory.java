package parkinglot.factory;

import parkinglot.model.Vehicle;
import parkinglot.enums.VehicleType;

/**
 * Factory Pattern - Create vehicles
 */
public class VehicleFactory {
    
    public static Vehicle createVehicle(String licensePlate, String type) {
        VehicleType vehicleType;
        
        switch (type.toUpperCase()) {
            case "BIKE":
            case "MOTORCYCLE":
                vehicleType = VehicleType.BIKE;
                break;
            case "CAR":
            case "SEDAN":
            case "SUV":
                vehicleType = VehicleType.CAR;
                break;
            case "TRUCK":
            case "BUS":
            case "VAN":
                vehicleType = VehicleType.TRUCK;
                break;
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
        
        return new Vehicle(licensePlate, vehicleType);
    }
    
    public static Vehicle createBike(String licensePlate) {
        return new Vehicle(licensePlate, VehicleType.BIKE);
    }
    
    public static Vehicle createCar(String licensePlate) {
        return new Vehicle(licensePlate, VehicleType.CAR);
    }
    
    public static Vehicle createTruck(String licensePlate) {
        return new Vehicle(licensePlate, VehicleType.TRUCK);
    }
}
