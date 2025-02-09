public class BankingSystem {
    public static void main(String[] args) {
        SavingsAccount savingsAccount = new SavingsAccount("501044556", "Akshay", 4000, 11.9);
        savingsAccount.getBalance();
        savingsAccount.deposit(1000);
        savingsAccount.withdraw(500);
        savingsAccount.getBalance();
        savingsAccount.calculateInterest();

        CurrentAccount currentAccount = new CurrentAccount("78789900","Steve",6000,100000);
        double currentBalance = currentAccount.getBalance();
        currentAccount.withdraw(50000);



    }
}
