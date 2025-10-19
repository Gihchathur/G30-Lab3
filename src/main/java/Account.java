import java.math.BigDecimal;

public class Account implements IAccount {

    /**
     * Current balance this account holds
     */
    private BigDecimal balance;
    /**
     * Currency used in this account, can be "SEK", "EUR", or "USD"
     */
    private String currency;
    /**
     * max_overdrawn is a non-negative number indicating how much the account can be "in the red"
     * The minimum balance of the account is -1 * max_overdrawn
     */
    private BigDecimal max_overdrawn;

    public BigDecimal getMaxOverdrawn() {
        return this.max_overdrawn;
    }

    public void setMaxOverdrawn(BigDecimal max_overdrawn) {
        if(max_overdrawn.compareTo(BigDecimal.ZERO) <= 0) {
            this.max_overdrawn = BigDecimal.ZERO;
        } else {
            this.max_overdrawn = max_overdrawn;
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(BigDecimal balance) {
        if(!(balance.compareTo(this.max_overdrawn.multiply(new BigDecimal(-1))) <= 0)) {
            this.balance = balance;
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.currency = "SEK";
        this.max_overdrawn = BigDecimal.ZERO;
    }

    public Account(BigDecimal starting_balance, String currency, BigDecimal max_overdrawn) {
        this.balance = starting_balance;
        this.currency = currency;
        if(max_overdrawn.compareTo(BigDecimal.ZERO) <= 0) {
            this.max_overdrawn = BigDecimal.ZERO;
        } else {
            this.max_overdrawn = max_overdrawn;
        }
    }

    @Override
    public BigDecimal withdraw(BigDecimal requestedAmount) {
        return this.balance.subtract(requestedAmount);
    }

    @Override
    public BigDecimal deposit(BigDecimal amount_to_deposit) {
        return this.balance.add(amount_to_deposit);
    }

    @Override
    public void convertToCurrency(String currencyCode, double rate) {
        this.currency = currencyCode;
        this.balance.multiply(new BigDecimal(rate));
    }

    @Override
    public void TransferToAccount(IAccount to_account) {
        to_account.deposit(this.balance);
    }

    @Override
    public BigDecimal withdrawAll() {
        if(this.balance.compareTo(this.max_overdrawn) <= 0) { // This can be read as "if (balance <= max_overdrawn)"
            return withdraw(balance);
        }
        return BigDecimal.ZERO;
    }
}
