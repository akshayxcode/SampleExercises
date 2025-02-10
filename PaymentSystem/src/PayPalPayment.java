public class PayPalPayment implements  PaymentMethod{
    String email;

public  PayPalPayment(String email) {
    this.email = email;
}
    @Override
    public void processPayment(double amount) {
        System.out.println("\"Processing Credit Card payment of $ " +amount+ " for email: " +email);
    }
}
