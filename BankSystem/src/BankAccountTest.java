import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
BankAccount bankAccount = new BankAccount("2222","Don",1000);
@Test
public void depositTest(){
    bankAccount.deposit(500);
    assertEquals(1500,bankAccount.getBalance());

    }
    @Test
    public void depositAmountZeroTest(){
        bankAccount.deposit(0);
        assertEquals(1000,bankAccount.getBalance());

    }

    @Test
    public void withdrawTest(){
        bankAccount.withdraw(500);
        assertEquals(500,bankAccount.getBalance());

    }



}