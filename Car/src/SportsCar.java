public class SportsCar extends Car implements TurboCharged {
    private boolean isTurboActivated;

    public SportsCar(String make, String model, int year, double speed) {
        super(make, model, year, speed);
        this.isTurboActivated = false;
    }

    @Override
    public void turboBoost() {
        isTurboActivated = true;
        speed += 50;
        System.out.println("Turbo activated");
    }
}
