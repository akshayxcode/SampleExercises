import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentProcessorTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testCreditCardPayment() {
        PaymentMethod creditCard = new CreditCardPayment("1122334455","Adam");
        PaymentProcessor.processTransaction(creditCard, 100.0);

        String expectedOutput = "Processing credit card payment of $100.0";
        //assertTrue(outputStream.toString().trim().contains(expectedOutput), "Credit card payment failed");
    }
}