public class CreditCardPayment implements PaymentMethod{
    String cardNumber;
    String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName){
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;

    }
    @Override
    public String processPayment(double amount) {
        amount += amount * 2 / 100;
        return "Processing Credit Card payment of $ " +amount+ " for card number: " +cardNumber;
    }
}
