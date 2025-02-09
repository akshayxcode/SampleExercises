public class Hatchback extends Car implements Bootspace {
    private String transmission;
    private int bootspaceCapacity;
    public Hatchback(String make, String model, int year, double speed, String transmission, int bootspaceCapacity) {
        super(make, model, year, speed);
        this.transmission = transmission;
        this.bootspaceCapacity = bootspaceCapacity;
    }
    public void transmissionDetails() {
        System.out.println(transmission + " car");
    }

    @Override
    public void bootspaceCapacity() {
        System.out.println("Bootspace capacity is: " + bootspaceCapacity);
    }
}

