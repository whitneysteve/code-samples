package bank.service;

import bank.model.Account;

import java.util.List;

/**
 * Describes the interface for working with {@link Account} records.
 */
public interface AccountService {
    /**
     * Create an {@link Account}.
     *
     * @param name the name of the account holder.
     * @param startingBalanceMajorUnits the starting balance of the account,
     *                                  in minor units.
     * @return the created {@link Account}
     * @throws ServiceException where the account cannot be created.
     */
    Account createAccount(
            final String name,
            final Integer startingBalanceMajorUnits
    ) throws ServiceException;

    /**
     * Find an {@link Account}.
     *
     * @param accountNumber the number of the {@link Account}.
     * @return the found {@link Account} or null if no account is found by
     * the provided number.
     * @throws ServiceException where the query cannot be completed.
     */
    Account findAccount(final Integer accountNumber) throws ServiceException;

    /**
     * Query for an account number by matching a {@link String}.
     *
     * @param query the query {@link String} to match by.
     * @return a {@link List} of found account numbers.
     * @throws ServiceException where the query cannot be completed.
     */
    List<String> queryAccountNumbers(
            final String query
    ) throws ServiceException;
}
