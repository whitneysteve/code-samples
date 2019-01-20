package bank.service;

import bank.model.Account;

/**
 * Describes the interface through which transfers are made.
 */
public interface TransferService {
    /**
     * Transfer funds from one account to another.
     *
     * @param accountFrom the {@link Account} the funds should be transferred
     *                    from.
     * @param accountTo the {@link Account} the funds should be transferred to.
     * @param amount the amount, in minor units, to transfer.
     * @throws ServiceException where the transfer cannot be completed.
     */
    void transfer(
            Account accountFrom,
            Account accountTo,
            Integer amount
    ) throws ServiceException;

}
