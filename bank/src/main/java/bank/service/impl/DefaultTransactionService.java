package bank.service.impl;

import bank.model.Account;
import bank.model.Transaction;
import bank.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the {@link TransactionService}. Stores {@link Transaction}s in
 * memory.
 */
@Service
public class DefaultTransactionService implements TransactionService {

    /**
     * Map used to store the {@link Transaction}s per {@link Account} in
     * memory.
     */
    private static Map<Integer, List<Transaction>> transactions =
            new HashMap<Integer, List<Transaction>>();

    /**
     * Query transactions for an account. Pagination is not implemented and
     * all transactions will be returned.
     *
     * @param account the {@link Account} whose transactions should be queried.
     * @param pageStart the starting page to retrieve.
     * @param pageSize the size of the page to retrieve.
     * @return the {@link Transaction}s for the account.
     */
    public final List<Transaction> getTransactions(
            final Account account,
            final int pageStart,
            final int pageSize
    ) {
        // TODO implement paging
        return transactions.get(account.getAccountNumber());
    }

    /**
     * Record a lodgement transaction.
     *
     * @param account the {@link Account} the amount was lodged to.
     * @param amount the amount, in minor units, that was lodged.
     * @return the created {@link Transaction}.
     */
    public final Transaction lodgement(
            final Account account,
            final Integer amount
    ) {
        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setAmountMinorUnits(amount);

        saveTransaction(account, transaction);

        return transaction;
    }

    /**
     * Record a withdrawl transaction.
     *
     * @param account the {@link Account} the amount was withdrawn from.
     * @param amount the amount, in minor units, that was withdrawn.
     * @return the created {@link Transaction}.
     */
    public final Transaction withdrawl(
            final Account account,
            final Integer amount
    ) {
        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setAmountMinorUnits(0 - amount);

        saveTransaction(account, transaction);

        return transaction;
    }

    /**
     * Save a {@link Transaction} to the data layer.
     *
     * @param account the {@link Account} to save the {@link Transaction} to.
     * @param transaction the {@link Transaction} to save.
     */
    private void saveTransaction(
            final Account account,
            final Transaction transaction
    ) {
        if (transactions.containsKey(account.getAccountNumber())) {
            List<Transaction> accountTransactions =
                    transactions.get(account.getAccountNumber());
            accountTransactions.add(transaction);
            transactions.put(account.getAccountNumber(), accountTransactions);
        } else {
            List<Transaction> accountTransactions =
                    new ArrayList<Transaction>();
            accountTransactions.add(transaction);
            transactions.put(account.getAccountNumber(), accountTransactions);
        }
    }
}
