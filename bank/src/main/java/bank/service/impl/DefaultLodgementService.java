package bank.service.impl;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import bank.service.LodgementService;
import bank.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultLodgementService implements LodgementService {

    @Autowired
    AccountDataAccess accountDataAccess;

    @Override
    public void lodge( Account account, Integer amount ) throws ServiceException {

        account.setBalanceMinorUnits( account.getBalanceMinorUnits() + amount );

        try {

            accountDataAccess.update( account );

        } catch( DataAccessException e ) {

            throw new ServiceException( e.getMessage(), e );

        }

    }

}
