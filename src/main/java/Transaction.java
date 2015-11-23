import java.math.BigDecimal;

/**
 * Created by hossein on 11/21/15.
 */
public class Transaction {
    private String id;
    private String type;
    private BigDecimal amount;
    private String deposit;

    public Transaction(String id, String type, String amount, String deposit) {
        this.id = id;
        this.type = type;
        amount = amount.replace(",", "");
        this.amount = new BigDecimal(Integer.parseInt(amount));
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "\ntransactionId: " + id + "\ntransactionType: " + type + "\ntransactionAmount: " + amount +
                "\ntransactionDeposit: " + deposit;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDeposit() {
        return deposit;
    }
}
