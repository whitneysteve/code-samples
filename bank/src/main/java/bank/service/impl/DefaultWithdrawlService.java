package bank.service.impl;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import bank.service.ServiceException;
import bank.service.WithdrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultWithdrawlService implements WithdrawlService {

    @Autowired
    AccountDataAccess accountDataAccess;

    @Override
    public void withdraw( Account account, Integer amount ) throws ServiceException {

        if( account.getBalanceMinorUnits() - amount < 0 ) {

            throw new ServiceException( "Insufficient funds" );

        }

        account.setBalanceMinorUnits( account.getBalanceMinorUnits() - amount );

        try {

            accountDataAccess.update( account );

        } catch( DataAccessException e ) {

            throw new ServiceException( e.getMessage(), e );

        }

    }

}
