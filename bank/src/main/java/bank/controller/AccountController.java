package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Controller to process all account related requests.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * Service layer access.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Create an account.
     *
     * @param name the name of the account holder.
     * @return rendered JSON response, including error scenarios.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public final @ResponseBody String create(
            @RequestParam(value = "name") final String name
    ) {
        Account account;
        Error error;
        ObjectMapper mapper = new ObjectMapper();

        try {
            account = accountService.createAccount(name, 0);
            return mapper.writeValueAsString(account);
        } catch (Exception e) {
            error = new Error(e.getMessage());
        }

        try {
            return mapper.writeValueAsString(error);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Query an account by a serarch {@link String}.
     *
     * @param query the query {@link String} to use to search.
     * @return rendered JSON response, including error scenarios.
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public final @ResponseBody String queryAccount(
            @RequestParam(value = "query") final String query
    ) {
        List<String> accountNumbers;
        Error error;
        ObjectMapper mapper = new ObjectMapper();

        try {
            accountNumbers = accountService.queryAccountNumbers(query);
            return mapper.writeValueAsString(accountNumbers);
        } catch (Exception e) {
            error = new Error(e.getMessage());
        }

        try {
            return mapper.writeValueAsString(error);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Get the balance on an account.
     *
     * @param accountNumber the number of the account to check the balance of.
     * @return rendered JSON response, including error scenarios.
     */
    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public final @ResponseBody String balance(
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
