import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    Car car = new Car("toyota","corolla",2021,100);

    @Test
    public void accelerateTest(){
        car.accelarate(50);
        assertEquals(150, car.displaySpeed());
    }

    @Test
    public void brakeTest(){
        car.brake(50);
        assertEquals(50, car.displaySpeed());
    }

}