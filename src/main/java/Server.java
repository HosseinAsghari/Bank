import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hossein on 11/21/15.
 */
public class Server{
    private int port;
    private List<Deposit> deposits;
    private String outLog;


    public int getPort() {
        return port;
    }

    public String getOutLog(){
        return outLog;
    }

    public List<Deposit> getDeposits(){
        return deposits;
    }


    public void depositMoney(BigDecimal amount, String depositId) throws DepositException{
        Deposit dep = find(depositId);
        if (dep.equals(null))
            throw new DepositException("Deposit not found.");
        synchronized (dep) {dep.addBalance(amount);}
    }

    private Deposit find(String depositId) {
        for (Deposit dep:deposits)
            if (dep.getId().equals(depositId))
                return dep;
        return null;
    }
}
