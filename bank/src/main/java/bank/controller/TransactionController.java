package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import bank.service.TransactionService;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping( "/transaction" )
public class TransactionController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping( value = "/list", method = RequestMethod.GET )
    public
    @ResponseBody
    String welcome( @RequestParam( value = "account" ) String accountNumber ) {

        Account account = null;
        Error error;
        ObjectMapper mapper = new ObjectMapper();

        try {

            account = accountService.findAccount( Integer.parseInt( accountNumber ) );

        } catch( NumberFormatException e ) {

            // Nothing - account not found

        } catch( ServiceException e ) {

            // Nothing - account not found

        }

        // TODO create error handler - exception code is generic
        try {

            if( account != null ) {

                return mapper.writeValueAsString( transactionService.getTransactions( account, 0, 10 ) );

            } else {

                error = new Error();
                error.message = "Account [" + accountNumber + "] not found";

            }

        } catch( NumberFormatException e ) {

            error = new Error();
            error.message = "Invlaid amount";

        } catch( JsonMappingException e ) {

            error = new Error();
            error.message = e.getMessage();

        } catch( JsonGenerationException e ) {

            error = new Error();
            error.message = e.getMessage();

        } catch( IOException e ) {

            error = new Error();
            error.message = e.getMessage();

        }

        try {

            return mapper.writeValueAsString( error );

        } catch( IOException e ) {

            e.printStackTrace();
            return "";

        }

    }

}
