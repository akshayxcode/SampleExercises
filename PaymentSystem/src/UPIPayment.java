public class UPIPayment implements  PaymentMethod{
    String upiId;

public UPIPayment(String upiId) {
    this.upiId = upiId;
}
    @Override
    public void processPayment(double amount) {
        System.out.println("\"Processing UPI payment of $ " +amount+ " for upiID: " + upiId);
    }
}
