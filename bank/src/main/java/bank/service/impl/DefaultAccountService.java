package bank.service.impl;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the {@link AccountService}.
 */
@Service
public class DefaultAccountService implements AccountService {

    /**
     * Data access layer reference.
     */
    @Autowired
    private AccountDataAccess accountDataAccess;

    /**
     * The maximum amounts to return in search results.
     */
    private static final int MAX_ACCOUNT_QUERY_RESULTS = 10;

    /**
     * Create an {@link Account}.
     *
     * @param name the name of the account holder.
     * @param startingBalanceMajorUnits the starting balance of the account,
     *                                  in minor units.
     * @return the created {@link Account}
     * @throws ServiceException where the account cannot be created.
     */
    @Override
    public final Account createAccount(
            final String name,
            final Integer startingBalanceMajorUnits
    ) throws ServiceException {
        if (!validateAccountDetails(name, startingBalanceMajorUnits)) {
            throw new ServiceException("Account validation failed");
        }

        Account account = new Account();
        account.setName(name);
        account.setBalanceMinorUnits(startingBalanceMajorUnits);

        try {
            return accountDataAccess.createAccount(account);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find an {@link Account}.
     *
     * @param accountNumber the number of the {@link Account}.
     * @return the found {@link Account} or null if no account is found by
     * the provided number.
     * @throws ServiceException where the query cannot be completed.
     */
    public final Account findAccount(
            final Integer accountNumber
    ) throws ServiceException {
        try {
            return accountDataAccess.findAccount(accountNumber);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Query for an account number by matching a {@link String}.
     *
     * @param query the query {@link String} to match by.
     * @return a {@link List} of found account numbers.
     * @throws ServiceException where the query cannot be completed.
     */
    public final List<String> queryAccountNumbers(
            final String query
    ) throws ServiceException {
        if (!validateAccountNumberQuery(query)) {
            throw new ServiceException("Query string validation failed");
        }

        try {
            return accountDataAccess.queryAccountNumbers(
                    query,
                    MAX_ACCOUNT_QUERY_RESULTS
            );
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Validate the account details. Currently unimplemented.
     *
     * @param name the name of the account holder.
     * @param startingBalanceMajorUnits the starting balance of the account,
     *                                  in minor units.
     * @return true if the account details are valid, false if not.
     */
    private boolean validateAccountDetails(
            final String name,
            final Integer startingBalanceMajorUnits
    ) {
        return name.trim().length() > 0 && startingBalanceMajorUnits >= 0;
    }

    /**
     * Validate an account query {@link String}.
     *
     * @param query the query {@link String} to execute.
     * @return true if the query {@link String} is valid, false if not.
     */
    private boolean validateAccountNumberQuery(final String query) {
        return query.trim().length() > 0;
    }
}
