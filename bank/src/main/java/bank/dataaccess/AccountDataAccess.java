package bank.dataaccess;

import bank.model.Account;

import java.util.List;

/**
 * Describes the interface to the account access data layer.
 */
public interface AccountDataAccess {

    /**
     * Create an account.
     *
     * @param account the proposed account object to create.
     * @return the created {@link Account}.
     * @throws DataAccessException if there is an error creating the proposed
     *                             account.
     */
    Account createAccount(final Account account) throws DataAccessException;

    /**
     * Find a account by it's account number.
     *
     * @param accountNumber the account number to search for.
     * @return the {@link Account} if found by number, or null if the account
     * is not found
     * @throws DataAccessException if there is an error looking up the account.
     */
    Account findAccount(
            final Integer accountNumber
    ) throws DataAccessException;

    /**
     * Update an account.
     *
     * @param account the {@link Account} object with the proposed changes.
     * @return the updated {@link Account} object.
     * @throws DataAccessException if there is an error updating the
     * {@link Account}.
     */
    Account update(final Account account) throws DataAccessException;

    /**
     * Query accounts by a query string. Maximum result sizes are not
     * available in the in-memory implementation.
     *
     * @param query the query to search accounts for.
     * @param maxSize not implemented.
     * @return the {@link List} of accounts found.
     * @throws DataAccessException if there is an issue searching for accounts.
     */
    List<String> queryAccountNumbers(
            final String query,
            final int maxSize
    ) throws DataAccessException;
}
