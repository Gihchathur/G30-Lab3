import java.math.BigDecimal;

public interface IAccount {

    /*
     * Method for withdrawing specified amount of money from account
     * After a withdrawal, the balance can be negative, up to minus the max_overdrawn amount.
     * The balance shall never be lower than that.
     * For example, if max_overdrawn is 100, the balance can be at lowest -100, but shall not be -101 or less.
     * */
    public abstract BigDecimal withdraw(BigDecimal requested_amount);

    /*
     * Method that withdraws all the money from the account
     * */
    public abstract BigDecimal withdrawAll();

    /*
     * Method for adding money to account
     * */
    public abstract BigDecimal deposit(BigDecimal amount_to_deposit);

    /*
     * Method for converting you money into different currency
     *
     * the method returns true if the conversion has been performed
     * false if the conversion fails
     * */
    public abstract void convertToCurrency(String currency_code, double rate);

    /*
     * Method that makes transfer from one account to another.
     * The transfer should be possible if and only if both accounts hold the same currency.
     * */

    public abstract void TransferToAccount(IAccount account);
}
