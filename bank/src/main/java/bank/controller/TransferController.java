package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import bank.service.TransactionService;
import bank.service.TransferService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to process all transfer related requests.
 */
@Controller
@RequestMapping("/transfer")
public class TransferController {

    /**
     * Service layer access.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Service layer access.
     */
    @Autowired
    private TransferService transferService;

    /**
     * Service layer access.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Transfer funds from one account to another.
     *
     * @param toAccountNumber the account number of the account to transfer
     *                        the funds to.
     * @param fromAccountNumber the account number of the account to transfer
     *                          funds from.
     * @param amount the amount to transfer, in minor units.
     * @return rendered JSON response, including error scenarios.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public final @ResponseBody String transfer(
            @RequestParam(value = "accountTo") final String toAccountNumber,
            @RequestParam(value = "accountFrom") final String fromAccountNumber,
            @RequestParam(value = "amount") final String amount
    ) {

        Account accountTo = null;
        Account accountFrom = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            accountTo = accountService.findAccount(
                    Integer.parseInt(toAccountNumber)
            );
            accountFrom = accountService.findAccount(
                    Integer.parseInt(fromAccountNumber)
            );
        } catch (NumberFormatException e) {
            // Nothing - account not found
        } catch (ServiceException e) {
            // Nothing - account not found
        }

        try {
            if (accountTo == null) {
                return new Error(
                        "Account [" + toAccountNumber + "] not found"
                ).toJson(mapper);
            } else if (accountFrom == null) {
                return new Error(
                        "Account [" + fromAccountNumber + "] not found"
                ).toJson(mapper);
            } else {
                Integer amountMinorUnits = Integer.parseInt(amount);
                transferService.transfer(
                        accountFrom,
                        accountTo,
                        amountMinorUnits
                );
                transactionService.withdrawl(accountFrom, amountMinorUnits);
                transactionService.lodgement(accountTo, amountMinorUnits);

                return mapper.writeValueAsString(
                        new Account[]{accountFrom, accountTo}
                );
            }
        } catch (NumberFormatException e) {
            return new Error("Invalid amount").toJson(mapper);
        } catch (Exception e) {
            return new Error(e.getMessage()).toJson(mapper);
        }
    }
}
