public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    double balance;

    public BankAccount(String accountNumber, String accountHolderName, double balance){
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public void deposit(double amount){
        balance += amount;
        System.out.println("Deposited Amount:" + amount);
    }
    public void withdraw(double amount){
        balance -= amount;
        System.out.println("Withdraw Amount:" + amount);
    }

    public double getBalance() {
        System.out.println("Your balance is : "+balance);
        return balance;
    }

}
