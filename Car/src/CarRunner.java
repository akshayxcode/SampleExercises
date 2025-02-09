public class CarRunner {
    public static void main(String[] args) {
        Hatchback hatchback = new Hatchback("volkswagon","polo",2018,100,"manual",200);
        hatchback.displayDetails();
        hatchback.accelarate(70);
        hatchback.brake(30);
        hatchback.transmissionDetails();
        hatchback.bootspaceCapacity();


        SportsCar sportCar = new SportsCar("porche", "911",2021,190);

        sportCar.turboBoost();
        sportCar.accelarate(20);
        sportCar.brake(90);
        sportCar.displayDetails();
    }
}