public class SavingsAccount extends BankAccount implements Interest {
    private final double interestRte;

    public SavingsAccount(String accountNumber, String accountHolderName, double balance, double interestRte) {
        super(accountNumber, accountHolderName, balance);
        this.interestRte = interestRte;
    }

    @Override
    public double calculateInterest() {
        return balance * interestRte / 100;
    }
}
