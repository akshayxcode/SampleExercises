import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrentAccountTest {
    CurrentAccount currentAccount = new CurrentAccount("3300334","Zam",10000,30000);

    @Test
    public void withdrawnSuccessfulTest() {
        currentAccount.withdraw(20000);

    }

}