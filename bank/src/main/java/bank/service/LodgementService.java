package bank.service;

import bank.model.Account;

public interface LodgementService {

    void lodge( Account account, Integer amount ) throws ServiceException;

}
