package bank.service.impl;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import bank.service.ServiceException;
import bank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTransferService implements TransferService {

    @Autowired
    AccountDataAccess accountDataAccess;

    @Override
    public void transfer( Account accountFrom, Account accountTo, Integer amount ) throws ServiceException {

        if( accountFrom.getBalanceMinorUnits() - amount < 0 ) {

            throw new ServiceException( "Insufficient funds" );

        }

        accountFrom.setBalanceMinorUnits( accountFrom.getBalanceMinorUnits() - amount );
        accountTo.setBalanceMinorUnits( accountTo.getBalanceMinorUnits() + amount );

        try {

            accountDataAccess.update( accountFrom );
            accountDataAccess.update( accountTo );

        } catch( DataAccessException e ) {

            throw new ServiceException( e.getMessage(), e );

        }

    }

}
