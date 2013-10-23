package socketp.client;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ClientResponseHandlerTest {

    private ClientResponseHandler _clientClientResponseHandler;

    @Before
    public void setup() {

        _clientClientResponseHandler = new ClientResponseHandler();

    }

    @Test
    public void shouldParseResponse() throws IOException {

        Assert.assertEquals( "response", _clientClientResponseHandler.handleResponse( new ByteArrayInputStream( "response".getBytes() ) ) );

    }

    @Test
    public void shouldParseMultiLineResponse() throws IOException {

        Assert.assertEquals( "response1", _clientClientResponseHandler.handleResponse( new ByteArrayInputStream( "response1\response2".getBytes() ) ) );

    }

    @Test
    public void shouldThrowErrorForErrorMessage() throws IOException {

        Exception error = null;

        try {

            _clientClientResponseHandler.handleResponse( new ByteArrayInputStream( "error:response".getBytes() ) );

        } catch( Exception e ) {

            error = e;

        }

        if( error == null ) {

            Assert.fail( "Expected error" );
        }

        Assert.assertEquals( "error:response", error.getMessage() );

    }

    @Test
    public void shouldReturnNullForEmptyInput() throws IOException {

        Assert.assertNull( _clientClientResponseHandler.handleResponse( new ByteArrayInputStream( "".getBytes() ) ) );

    }

    @Test(expected = IllegalArgumentException.class)
    public void errorForNullInputStream() throws IOException {

        _clientClientResponseHandler.handleResponse( null );

    }

}
