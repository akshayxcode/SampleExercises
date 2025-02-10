public class CreditCardPayment implements PaymentMethod{
    String cardNumber;
    String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName){
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;

    }
    @Override
    public void processPayment(double amount) {
        System.out.println("\"Processing Credit Card payment of $ " +amount+ " for card number: " +cardNumber);
    }
}
