package bank.model;

/**
 * Represents a transaction performed on an {@link Account} at the bank.
 */
public class Transaction {

    /**
     * The {@link Account} the transaction was performed on.
     */
    private Account account;
    /**
     * The amount of the transaction. Negative amounts refer to withdrawls,
     * positive to lodgements.
     */
    private Integer amountMinorUnits;

    /**
     * @return the {@link Account} the transaction refers to.
     */
    public final Account getAccount() {
        return account;
    }

    /**
     * @param newAccount the {@link Account} the transaction refers to.
     */
    public final void setAccount(final Account newAccount) {
        this.account = newAccount;
    }

    /**
     * @return the amiunt the transaction refers to, in minor units.
     */
    public final Integer getAmountMinorUnits() {
        return amountMinorUnits;
    }

    /**
     * @param newAmountMinorUnits the new amount for the transaction, in
     *                            minor units.
     */
    public final void setAmountMinorUnits(final Integer newAmountMinorUnits) {
        this.amountMinorUnits = newAmountMinorUnits;
    }
}
