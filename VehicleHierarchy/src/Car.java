public class Car extends  Vehicle{
    int numberOfDoors;

    public Car(int speed, int fuelCapacity, int fuelLevel, int numberOfDoors) {
        super(speed, fuelCapacity, fuelLevel);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    void drive() {
        System.out.println("\"The car is driving at "+speed+" km/h.");
    }
}
