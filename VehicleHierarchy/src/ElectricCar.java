public class ElectricCar extends Vehicle implements Electric{
    int batteryLevel = 0;

    public ElectricCar(int speed, int batteryLevel) {
        super(speed);
        this.batteryLevel = batteryLevel;
    }

    @Override
    public void chargeBattery() {
        batteryLevel = 100;
        System.out.println("The electric car is fully charged." );
    }

    @Override
    void drive() {
        System.out.println("The electric car is driving at " +speed+ "km/h with " +batteryLevel+"% battery.");
    }
}
