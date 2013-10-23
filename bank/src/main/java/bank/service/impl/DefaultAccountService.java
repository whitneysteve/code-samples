package bank.service.impl;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAccountService implements AccountService {

    @Autowired
    private AccountDataAccess accountDataAccess;

    private static final int MAX_ACCOUNT_QUERY_RESULTS = 10;

    @Override
    public Account createAccount( String name, Integer startingBalanceMajorUnits ) throws ServiceException {

        if( !validateAccountDetails( name, startingBalanceMajorUnits ) ) {

            throw new ServiceException( "TODO error details here" );

        }

        Account account = new Account();
        account.setName( name );
        account.setBalanceMinorUnits( startingBalanceMajorUnits );

        try {

            return accountDataAccess.createAccount( account );

        } catch( DataAccessException e ) {

            throw new ServiceException( e );

        }

    }

    public Account findAccount( Integer accountNumber ) throws ServiceException {

        try {

            return accountDataAccess.findAccount( accountNumber );

        } catch( DataAccessException e ) {

            throw new ServiceException( e.getMessage(), e );

        }

    }

    public List<String> queryAccountNumbers( String query ) throws ServiceException {

        if( !validateAccountNumberQuery( query ) ) {

            throw new ServiceException( "TODO error details here" );

        }

        try {

            return accountDataAccess.queryAccountNumbers( query, MAX_ACCOUNT_QUERY_RESULTS );

        } catch( DataAccessException e ) {

            throw new ServiceException( e );

        }

    }

    private boolean validateAccountDetails( String name, Integer startingBalanceMajorUnits ) {

        // TODO validate account details
        return true;

    }

    private boolean validateAccountNumberQuery( String query ) {

        // TODO validate account details
        return true;

    }

}
