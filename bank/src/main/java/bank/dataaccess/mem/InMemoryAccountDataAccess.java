package bank.dataaccess.mem;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InMemoryAccountDataAccess implements AccountDataAccess {

    private static Map<Integer, Account> accounts = new HashMap<Integer, Account>();
    private static AtomicInteger accountSeq = new AtomicInteger( 0 );

    public Account createAccount( Account account ) throws DataAccessException {

        if( !validateAccount( account ) ) {

            throw new DataAccessException( "TODO: error details here" );

        }

        account.setAccountNumber( accountSeq.incrementAndGet() );

        accounts.put( account.getAccountNumber(), account );

        return account;

    }

    public Account update( Account account ) throws DataAccessException {

        if( !validateAccount( account ) ) {

            throw new DataAccessException( "TODO: error details here" );

        }

        accounts.put( account.getAccountNumber(), account );

        return account;

    }

    public Account findAccount( Integer accountNumber ) throws DataAccessException {

        return accounts.get( accountNumber );

    }

    public List<String> queryAccountNumbers( String query, int maxSize ) throws DataAccessException {

        // TODO silly method for in memory demonstration
        List<String> accountNumbers = new ArrayList<String>();
        Iterator<Integer> it = accounts.keySet().iterator();

        while( accountNumbers.size() <= maxSize && it.hasNext() ) {

            Integer accountNumber = it.next();

            if( ( "" + accountNumber ).startsWith( query ) ) {

                accountNumbers.add( "" + accountNumber );

            }

        }

        return accountNumbers;

    }

    private boolean validateAccount( Account account ) {

        // TODO validate account
        return true;

    }

}
