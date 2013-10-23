package bank.model;

public class Account {

    private Integer accountNumber;
    private String name;
    private Integer balanceMinorUnits;

    public Integer getAccountNumber() {

        return accountNumber;

    }

    public void setAccountNumber( Integer accountNumber ) {

        this.accountNumber = accountNumber;

    }

    public String getName() {

        return name;

    }

    public void setName( String name ) {

        this.name = name;

    }

    public Integer getBalanceMinorUnits() {

        return balanceMinorUnits;

    }

    public void setBalanceMinorUnits( Integer balanceMinorUnits ) {

        this.balanceMinorUnits = balanceMinorUnits;

    }

}
