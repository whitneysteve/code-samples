package bank.service;

import bank.model.Account;

/**
 * Describes the interface to perform a withdrawl on an {@link Account}.
 */
public interface WithdrawlService {
    /**
     * Perform a withdrawl from an account.
     *
     * @param account the {@link Account} to withdraw from.
     * @param amount the amount, in minor units, to withdraw.
     * @throws ServiceException where the withdrawl cannot be completed.
     */
    void withdraw(Account account, Integer amount) throws ServiceException;
}
