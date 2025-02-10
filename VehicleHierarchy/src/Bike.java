public class Bike extends Vehicle {
    boolean hasGear;

    public Bike(int speed, int fuelCapacity, int fuelLevel, boolean hasGear) {
        super(speed, fuelCapacity, fuelLevel);
        this.hasGear = hasGear;
    }

    @Override
    void drive() {
        System.out.println("The bike is riding at "+speed+" km/h.");
    }
}
