package bank.service.impl;

import bank.model.Account;
import bank.model.Transaction;
import bank.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultTransactionService implements TransactionService {

    private static Map<Integer, List<Transaction>> transactions = new HashMap<Integer, List<Transaction>>();

    public List<Transaction> getTransactions( Account account, int pageStart, int pageSize ) {

        // TODO implement paging
        return transactions.get( account.getAccountNumber() );

    }

    public Transaction lodgement( Account account, Integer amount ) {

        Transaction transaction = new Transaction();

        transaction.setAccount( account );
        transaction.setAmountMinorUnits( amount );

        saveTransaction( account, transaction );

        return transaction;

    }

    public Transaction withdrawl( Account account, Integer amount ) {

        Transaction transaction = new Transaction();

        transaction.setAccount( account );
        transaction.setAmountMinorUnits( 0 - amount );

        saveTransaction( account, transaction );

        return transaction;

    }

    private void saveTransaction( Account account, Transaction transaction ) {

        if( transactions.containsKey( account.getAccountNumber() ) ) {

            List<Transaction> accountTransactions = transactions.get( account.getAccountNumber() );
            accountTransactions.add( transaction );
            transactions.put( account.getAccountNumber(), accountTransactions );

        } else {

            List<Transaction> accountTransactions = new ArrayList<Transaction>();
            accountTransactions.add( transaction );
            transactions.put( account.getAccountNumber(), accountTransactions );

        }

    }

}
