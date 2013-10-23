package bank.service;

import bank.model.Account;

public interface TransferService {

    void transfer( Account accountFrom, Account accountTo, Integer amount ) throws ServiceException;

}
