package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
import bank.service.TransactionService;
import bank.service.TransferService;
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
@RequestMapping( "/transfer" )
public class TransferController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public
    @ResponseBody
    String transfer( @RequestParam( value = "accountTo" ) String toAccountNumber, @RequestParam( value = "accountFrom" ) String fromAccountNumber, @RequestParam( value = "amount" ) String amount ) {

        Account accountTo = null;
        Account accountFrom = null;
        Error error;
        ObjectMapper mapper = new ObjectMapper();

        try {

            accountTo = accountService.findAccount( Integer.parseInt( toAccountNumber ) );
            accountFrom = accountService.findAccount( Integer.parseInt( fromAccountNumber ) );

        } catch( NumberFormatException e ) {

            // Nothing - account not found

        } catch( ServiceException e ) {

            // Nothing - account not found

        }

        // TODO create error handler - exception code is generic
        try {

            if( accountTo == null ) {

                error = new Error();
                error.message = "Account [" + toAccountNumber + "] not found";

            } else if( accountFrom == null ) {

                error = new Error();
                error.message = "Account [" + fromAccountNumber + "] not found";

            } else {

                Integer amountMinorUnits = Integer.parseInt( amount );
                transferService.transfer( accountFrom, accountTo, amountMinorUnits );
                transactionService.withdrawl( accountFrom, amountMinorUnits );
                transactionService.lodgement( accountTo, amountMinorUnits );

                return mapper.writeValueAsString( new Account[]{ accountFrom, accountTo } );

            }

        } catch( NumberFormatException e ) {

            error = new Error();
            error.message = "Invlaid amount";

        } catch( ServiceException e ) {

            error = new Error();
            error.message = e.getMessage();

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