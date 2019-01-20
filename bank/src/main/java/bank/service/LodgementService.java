package bank.service;

import bank.model.Account;

/**
 * Describes the interface used when making a lodgment.
 */
public interface LodgementService {
    /**
     * Make a lodgement to an {@link Account}.
     *
     * @param account the {@link Account} to lodge to.
     * @param amount the amount, in minor units, to lodge.
     * @throws ServiceException where there is an error with the lodgement.
     */
    void lodge(
            final Account account,
            final Integer amount
    ) throws ServiceException;
}
