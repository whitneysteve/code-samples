package bank.model;

/**
 * Represents a valid account at the bank.
 */
public class Account {
    /**
     * The number on the account that uniquely identifies it.
     */
    private Integer accountNumber;
    /**
     * The name of the account holder.
     */
    private String name;
    /**
     * The current balance of the account, in minor units.
     */
    private Integer balanceMinorUnits;

    /**
     * @return the account number.
     */
    public final Integer getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param newAccountNumber the new account number to set.
     */
    public final void setAccountNumber(final Integer newAccountNumber) {
        this.accountNumber = newAccountNumber;
    }

    /**
     * @return the name of the account holder.
     */
    public final String getName() {
        return name;
    }

    /**
     * @param newName the new name to set on the account.
     */
    public final void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return the balance of the account, in minor units.
     */
    public final Integer getBalanceMinorUnits() {
        return balanceMinorUnits;
    }

    /**
     * @param newBalanceMinorUnits the new balance on the account, in minor
     *                             units.
     */
    public final void setBalanceMinorUnits(final Integer newBalanceMinorUnits) {
        this.balanceMinorUnits = newBalanceMinorUnits;
    }
}
