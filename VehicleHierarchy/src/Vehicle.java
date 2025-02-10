abstract class Vehicle {
    int speed = 0;
    int fuelCapacity;
    int fuelLevel;

    public Vehicle(int speed){
        this.speed = speed;
    };

    public Vehicle(int speed, int fuelCapacity, int fuelLevel){
        this.speed = speed;
        this.fuelLevel = fuelLevel;
        this.fuelCapacity = fuelCapacity;
    }


    abstract void drive();

    public void refuel(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid fuel amount");
            return;
        }
        int newFuelLevel = fuelLevel + amount;
        if (newFuelLevel > fuelCapacity) {
            fuelLevel = fuelCapacity;
            System.out.println("Fuel tank is full");
        } else {
            fuelLevel = newFuelLevel;
            System.out.println("Refueled successfully. Current fuel level: " + fuelLevel);
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
}
