public class UPIPayment implements  PaymentMethod{
    String upiId;

public UPIPayment(String upiId) {
    this.upiId = upiId;
}
    @Override
    public String processPayment(double amount) {
        return "Processing UPI payment of $ " +amount+ " for upiID: " + upiId;
    }
}
