public class PayPalPayment implements  PaymentMethod{
    String email;

public  PayPalPayment(String email) {
    this.email = email;
}
    @Override
    public String processPayment(double amount) {
    if (amount > 0) {
            amount += 1;
        }
        return "Processing Paypal payment of $ " +amount+ " for email: " +email;
    }
}
