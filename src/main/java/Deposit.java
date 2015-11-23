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

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void addBalance(BigDecimal amount) throws DepositException {
        if (this.getBalance().add(amount).compareTo(this.getUpperLimit()) > 0)
            throw new DepositException("Deposit reached to upper bound.");
        if (this.getBalance().add(amount).compareTo(new BigDecimal(0)) < 0)
            throw new DepositException("There is not enough balance in the deposit as requested to withdraw.");
        this.balance.add(amount);
    }
}
