import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {
SavingsAccount savingsAccount = new SavingsAccount("112233","Adam",500,8.25);

@Test
    public void calculateInterestRateTest() {
    assertEquals(41.25,savingsAccount.calculateInterest());
}
}