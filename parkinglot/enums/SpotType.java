package parkinglot.enums;

public enum SpotType {
    COMPACT(1),     // For bikes
    REGULAR(2),     // For cars
    LARGE(3);       // For trucks (can also fit smaller vehicles)
    
    private final int size;
    
    SpotType(int size) {
        this.size = size;
    }
    
    public int getSize() {
        return size;
    }
}
