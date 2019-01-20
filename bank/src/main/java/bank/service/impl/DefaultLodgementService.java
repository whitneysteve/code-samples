package bank.service.impl;

import bank.dataaccess.AccountDataAccess;
import bank.dataaccess.DataAccessException;
import bank.model.Account;
import bank.service.LodgementService;
import bank.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the {@link LodgementService}.
 */
@Service
public class DefaultLodgementService implements LodgementService {

    /**
     * Data access layer reference.
     */
    @Autowired
    private AccountDataAccess accountDataAccess;

    /**
     * Make a lodgement to an {@link Account}.
     *
     * @param account the {@link Account} to lodge to.
     * @param amount the amount, in minor units, to lodge.
     * @throws ServiceException where there is an error with the lodgement.
     */
    @Override
    public final void lodge(
            final Account account,
            final Integer amount
    ) throws ServiceException {
        account.setBalanceMinorUnits(account.getBalanceMinorUnits() + amount);

        try {
            accountDataAccess.update(account);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
