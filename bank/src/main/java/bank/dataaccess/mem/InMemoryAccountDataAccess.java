package bank.dataaccess.mem;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implements an in-memory version of account storage.
 */
@Service
public class InMemoryAccountDataAccess implements AccountDataAccess {

    /**
     * Map used to store accounts in memory.
     */
    private static Map<Integer, Account> accounts =
            new HashMap<Integer, Account>();
    /**
     * The atomically controlled next account number.
     */
    private static AtomicInteger accountSeq =
            new AtomicInteger(0);

    /**
     * Create an account.
     *
     * @param account the proposed account object to create.
     * @return the created {@link Account}.
     * @throws DataAccessException if there is an error creating the proposed
     * account.
     */
    public final Account createAccount(
            final Account account
    ) throws DataAccessException {
        if (!validateAccount(account)) {
            throw new DataAccessException("Account is invalid");
        }
        account.setAccountNumber(accountSeq.incrementAndGet());
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    /**
     * Update an account.
     *
     * @param account the {@link Account} object with the proposed changes.
     * @return the updated {@link Account} object.
     * @throws DataAccessException if there is an error updating the
     * {@link Account}.
     */
    public final Account update(
            final Account account
    ) throws DataAccessException {
        if (!validateAccount(account)) {
            throw new DataAccessException("Updated account is invalid");
        }
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    /**
     * Find a account by it's account number.
     *
     * @param accountNumber the account number to search for.
     * @return the {@link Account} if found by number, or null if the account
     * is not found
     * @throws DataAccessException if there is an error looking up the account.
     */
    public final Account findAccount(
            final Integer accountNumber
    ) throws DataAccessException {
        return accounts.get(accountNumber);
    }

    /**
     * Query accounts by a query string. Maximum result sizes are not
     * available in the in-memory implementation.
     *
     * @param query the query to search accounts for.
     * @param maxSize not implemented.
     * @return the {@link List} of accounts found.
     * @throws DataAccessException if there is an issue searching for accounts.
     */
    public final List<String> queryAccountNumbers(
            final String query,
            final int maxSize
    ) throws DataAccessException {
        // Silly search method for in memory demonstration
        List<String> accountNumbers = new ArrayList<String>();
        Iterator<Integer> it = accounts.keySet().iterator();

        while (accountNumbers.size() <= maxSize && it.hasNext()) {
            Integer accountNumber = it.next();

            if (("" + accountNumber).startsWith(query)) {
                accountNumbers.add("" + accountNumber);
            }
        }

        return accountNumbers;
    }

    /**
     * Validate an account.
     *
     * @param account the {@link Account} to validate.
     * @return true if the {@link Account} is valid, false if not.
     */
    private boolean validateAccount(final Account account) {
        return account.getName().trim().length() > 0
                && account.getBalanceMinorUnits() >= 0;
    }
}
