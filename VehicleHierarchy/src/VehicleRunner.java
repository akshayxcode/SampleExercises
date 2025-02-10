public class VehicleRunner {
    public static void main(String[] args) {
        Car car = new Car(80,35,10,3);
        car.drive();
        car.refuel(10);
        car.getFuelLevel();

        Bike bike = new Bike(100,12,3,true);
        bike.drive();
        bike.refuel(4);

        ElectricCar electricCar = new ElectricCar(200,66);
        electricCar.drive();
        electricCar.chargeBattery();
    }
}
