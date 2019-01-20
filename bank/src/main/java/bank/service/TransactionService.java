package bank.service;

import bank.model.Account;
import bank.model.Transaction;

import java.util.List;

/**
 * Defines the interface through which {@link Transaction}s are tracked and
 * queried.
 */
public interface TransactionService {
    /**
     * Record a lodgement transaction.
     *
     * @param account the {@link Account} the amount was lodged to.
     * @param amount the amount, in minor units, that was lodged.
     * @return the created {@link Transaction}.
     */
    Transaction lodgement(final Account account, final Integer amount);

    /**
     * Record a withdrawl transaction.
     *
     * @param account the {@link Account} the amount was withdrawn from.
     * @param amount the amount, in minor units, that was withdrawn.
     * @return the created {@link Transaction}.
     */
    Transaction withdrawl(final Account account, final Integer amount);

    /**
     * Query transactions for an account. Queries should be paged.
     *
     * @param account the {@link Account} whose transactions should be queried.
     * @param pageStart the starting page to retrieve.
     * @param pageSize the size of the page to retrieve.
     * @return the {@link Transaction}s for the account.
     */
    List<Transaction> getTransactions(
            final Account account,
            final int pageStart,
            final int pageSize
    );
}
