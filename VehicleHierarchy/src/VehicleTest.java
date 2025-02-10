import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
public class VehicleTest {
    Car car = new Car(120,50,4,5);
    Bike bike = new Bike(80,15,2,true);

    @Test
    public void testFuelLeve(){
        car.refuel(20);
        assertEquals(24,car.getFuelLevel(),"Fuel level increase by 20");
    }
    @Test
    public void testDrive() {
        car.drive();
        assertTrue(car.getFuelLevel() < 24, "Fuel level should decrease after driving");
    }

    @Test
    public void bikeRunningRunningSpeedTest(){
        bike.drive();
        assertEquals(80,bike.getSpeed(),"Bike is running at speed of 80km/h");
    }


  
}