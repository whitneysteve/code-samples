package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.LodgementService;
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
 * Controller to process all lodgement related requests.
 */
@Controller
@RequestMapping("/lodgement")
public class LodgementController {

    /**
     * Service layer access.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Service layer access.
     */
    @Autowired
    private LodgementService lodgementService;

    /**
     * Service layer access.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Create a lodgement.
     *
     * @param accountNumber the account number being lodged to.
     * @param amount the amount being lodged, in minor units.
     * @return rendered JSON response, including error scenarios.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public final @ResponseBody String create(
            @RequestParam(value = "account") final String accountNumber,
            @RequestParam(value = "amount") final String amount
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
                Integer amountMinorUnits = Integer.parseInt(amount);
                lodgementService.lodge(account, amountMinorUnits);
                transactionService.lodgement(account, amountMinorUnits);

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
