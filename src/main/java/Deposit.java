import java.math.BigDecimal;

/**
 * Created by hossein on 11/21/15.
 */
public class Deposit {
    private String customer;
    private String id;
    private String initialBalance;
    private String upperBound;
    private BigDecimal balance;
    private BigDecimal upperLimit;

    @Override
    public String toString() {
        return "\ncustomer: " + customer + "\nid: " + id + "\nbalance: " + balance + "\nupperBound: " + upperLimit;
    }

    public void setDecimals() {
        balance = new BigDecimal(initialBalance.replace(",", ""));
        upperLimit = new BigDecimal(upperBound.replace(",", ""));
    }
}
