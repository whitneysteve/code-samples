package bank.service;

import bank.model.Account;

public interface WithdrawlService {

    void withdraw( Account account, Integer amount ) throws ServiceException;

}
