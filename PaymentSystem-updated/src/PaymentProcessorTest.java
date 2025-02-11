import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentProcessorTest {

    @Test
    public void testCreditCardPaymentWithFee() {
        CreditCardPayment creditCard = new CreditCardPayment("1122334455", "Adam");
        double amount = 100.0;
        String expectedOutput = "Processing Credit Card payment of $ 102.0 for card number: 1122334455";

        String actualOutput = creditCard.processPayment(amount);

        assertEquals(expectedOutput, actualOutput, "The processed payment message is incorrect.");
    }
    @Test
    public void testCreditCardPaymentWithZeroAmount() {
        CreditCardPayment creditCard = new CreditCardPayment("1234567890", "Alice");
        double amount = 0.0;
        String expectedOutput = "Processing Credit Card payment of $ 0.0 for card number: 1234567890";

        String actualOutput = creditCard.processPayment(amount);

        assertEquals(expectedOutput, actualOutput, "Processing zero amount should return correct output.");
    }
    @Test
    public void testPaypalPaymentWithFee() {
        PayPalPayment paypal = new PayPalPayment("john@gmil.com");
        double amount = 1000;
        String expectedOutput = "Processing Paypal payment of $ 1001.0 for email: john@gmil.com";
        String actualOutput = paypal.processPayment(amount);
        assertEquals(expectedOutput, actualOutput, "invalid output");
    }

    @Test
    public void testPaypalPaymentWithZeroAmount() {
        PayPalPayment paypal = new PayPalPayment("john@gmil.com");
        double amount = 0;
        String expectedOutput = "Processing Paypal payment of $ 0.0 for email: john@gmil.com";
        String actualOutput = paypal.processPayment(amount);
        assertEquals(expectedOutput, actualOutput, "invalid output");
    }
}