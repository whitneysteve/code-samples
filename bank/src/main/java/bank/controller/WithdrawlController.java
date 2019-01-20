package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import bank.service.TransactionService;
import bank.service.WithdrawlService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to process all withdrawl related requests.
 */
@Controller
@RequestMapping("/withdrawl")
public class WithdrawlController {

    /**
     * Service layer access.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Service layer access.
     */
    @Autowired
    private WithdrawlService withdrawlService;

    /**
     * Service layer access.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Withdraw from an account.
     *
     * @param accountNumber the number of the account from which to withdraw
     *                      funds.
     * @param amount the amount to withdraw, in minor units.
     * @return rendered JSON response, including error scenarios.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public final @ResponseBody String withdraw(
            @RequestParam(value = "account") final String accountNumber,
            @RequestParam(value = "amount") final String amount
    ) {
        Account account = null;
        Error error;
        ObjectMapper mapper = new ObjectMapper();

        try {
            account = accountService.findAccount(
                    Integer.parseInt(accountNumber)
            );
        } catch (NumberFormatException e) {
            // Nothing - account not found
        } catch (ServiceException e) {
            // Nothing - account not found
        }

        try {
            if (account != null) {
                Integer amountMinorUnits = Integer.parseInt(amount);
                withdrawlService.withdraw(account, amountMinorUnits);
                transactionService.withdrawl(account, amountMinorUnits);

                return mapper.writeValueAsString(account);
            } else {
                return new Error("Account [" + accountNumber + "] not found")
                        .toJson(mapper);
            }
        } catch (NumberFormatException e) {
            return new Error("Invalid amount").toJson(mapper);
        } catch (Exception e) {
            return new Error(e.getMessage()).toJson(mapper);
        }
    }
}
