/**
 * Created by hossein on 11/23/15.
 */
public class DepositException extends Exception {
    private static final long serialVersionUID = -5870171746375713110L;

    public DepositException() {}

    public DepositException(String errorLog) {
        super(errorLog);
    }
}
