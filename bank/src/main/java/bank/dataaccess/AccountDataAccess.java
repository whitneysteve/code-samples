package bank.dataaccess;

import bank.model.Account;

import java.util.List;

public interface AccountDataAccess {

    Account createAccount( Account account ) throws DataAccessException;

    Account findAccount( Integer accountNumber ) throws DataAccessException;

    Account update( Account account ) throws DataAccessException;

    List<String> queryAccountNumbers( String query, int maxSize ) throws DataAccessException;

}
