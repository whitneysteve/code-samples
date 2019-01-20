package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import bank.service.TransactionService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to process all transaction related requests.
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {

    /**
     * The default size of pages retrieved when querying account transactions.
     */
    private static int defaultPageSize = Integer.valueOf("10");

    /**
     * Service layer access.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Service layer access.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Get the transactions for an account.
     *
     * @param accountNumber the account number of the account to get
     *                      transactions for.
     * @return rendered JSON response, including error scenarios.
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public final @ResponseBody String welcome(
            @RequestParam(value = "account") final String accountNumber
    ) {
        Account account = null;
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
                return mapper.writeValueAsString(
                        transactionService.getTransactions(
                                account,
                                0,
                                defaultPageSize
                        )
                );
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
