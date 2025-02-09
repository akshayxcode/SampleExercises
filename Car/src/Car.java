public class Car {
    private String make;
    private String model;
    private int year;
    double speed;

    public Car(String make, String model, int year, double speed) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.speed = speed;
    }

    public void accelarate(double increment){
        speed += increment;
        System.out.println("Accelerated by: " + speed);
    }

    public void brake(double decrement) {
        if (decrement < speed) {
            speed -= decrement;
        }else  {
            speed = 0;
            System.out.println("Your car stopped");
        }
    }


    public void displayDetails() {
        System.out.println(make);
        System.out.println(model);
        System.out.println(year);
        System.out.println("Current speed : " + speed);
    }


    // created for test case
    public double displaySpeed() {
        System.out.println("Current speed : " + speed);
        return speed;
    }

}
