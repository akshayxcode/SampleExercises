public class PaymentProcessor  {

    public static void processTransaction(PaymentMethod paymentMethod, double amount) {
        paymentMethod.processPayment(amount);
    }

    public static void main(String[] args) {
        PaymentMethod creditCard = new CreditCardPayment("550044556677","Akshay");
        PaymentMethod paypal = new PayPalPayment("akshay12@gamil.com");
        PaymentMethod upi = new UPIPayment("akzzay12@okhdfc");

        processTransaction(creditCard,200);
        processTransaction(paypal,500);
        processTransaction(upi,800);

    }


}
