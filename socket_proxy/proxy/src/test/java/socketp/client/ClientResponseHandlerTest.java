package socketp.client;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ClientResponseHandlerTest {

    private ClientResponseHandler clientClientResponseHandler;

    @Before
    public void setup() {
        clientClientResponseHandler = new ClientResponseHandler();
    }

    @Test
    public void shouldParseResponse() throws IOException {
        Assert.assertEquals(
            "response",
            clientClientResponseHandler.handleResponse(
                new ByteArrayInputStream("response".getBytes())
            )
        );
    }

    @Test
    public void shouldParseMultiLineResponse() throws IOException {
        Assert.assertEquals(
            "response1",
            clientClientResponseHandler.handleResponse(
                new ByteArrayInputStream("response1\response2".getBytes())
            )
        );
    }

    @Test
    public void shouldThrowErrorForErrorMessage() {
        Exception error = null;

        try {
            clientClientResponseHandler.handleResponse(
                new ByteArrayInputStream("error:response".getBytes())
            );
        } catch (Exception e) {
            error = e;
        }

        if (error == null) {
            Assert.fail("Expected error");
        }

        Assert.assertEquals("error:response", error.getMessage());
    }

    @Test
    public void shouldReturnNullForEmptyInput() throws IOException {
        Assert.assertNull(
            clientClientResponseHandler.handleResponse(
                new ByteArrayInputStream("".getBytes())
            )
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorForNullInputStream() throws IOException {
        clientClientResponseHandler.handleResponse(null);
    }
}
