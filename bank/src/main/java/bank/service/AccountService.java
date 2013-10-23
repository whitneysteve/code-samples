package bank.service;

import bank.model.Account;

import java.util.List;

public interface AccountService {

    Account createAccount( String name, Integer startingBalanceMajorUnits ) throws ServiceException;

    Account findAccount( Integer accountNumber ) throws ServiceException;

    List<String> queryAccountNumbers( String query ) throws ServiceException;

}
