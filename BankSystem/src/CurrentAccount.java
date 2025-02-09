public class CurrentAccount extends BankAccount{
    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolderName, double balance, double overdraftLimit) {
        super(accountNumber, accountHolderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if(amount < overdraftLimit) {
            balance -= amount;
            System.out.println("withdrawn: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount");
        }

    }

}
