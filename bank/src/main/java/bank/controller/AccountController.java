package bank.controller;

import bank.model.Account;
import bank.service.AccountService;
import bank.service.ServiceException;
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
import java.util.List;

@Controller
@RequestMapping( "/account" )
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public
    @ResponseBody
    String welcome( @RequestParam( value = "name" ) String name ) {

        Account account;
        Error error;
        ObjectMapper mapper = new ObjectMapper();

        // TODO create error handler - exception code is generic
        try {

            account = accountService.createAccount( name, 0 );

            return mapper.writeValueAsString( account );

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

    @RequestMapping( value = "/query", method = RequestMethod.GET )
    public
    @ResponseBody
    String queryAccount( @RequestParam( value = "query" ) String query ) {

        List<String> accountNumbers;
        Error error;
        ObjectMapper mapper = new ObjectMapper();

        // TODO create error handler - exception code is generic
        try {

            accountNumbers = accountService.queryAccountNumbers( query );

            return mapper.writeValueAsString( accountNumbers );

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

    @RequestMapping( value = "/balance", method = RequestMethod.GET )
    public
    @ResponseBody
    String balance( @RequestParam( value = "account" ) String accountNumber ) {

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

                return mapper.writeValueAsString( account );

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
