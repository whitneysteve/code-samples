package bank.model;

// TODO need types of transactions
// TODO need transaction description
// TODO need transaction transfer information if applicable
// TODO need transaction date
public class Transaction {

    Account account;
    Integer amountMinorUnits;

    public Account getAccount() {

        return account;

    }

    public void setAccount( Account account ) {

        this.account = account;

    }

    public Integer getAmountMinorUnits() {

        return amountMinorUnits;

    }

    public void setAmountMinorUnits( Integer amountMinorUnits ) {

        this.amountMinorUnits = amountMinorUnits;

    }

}
