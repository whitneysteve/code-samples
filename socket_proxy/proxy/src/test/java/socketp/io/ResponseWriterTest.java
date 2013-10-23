package socketp.io;

import socketp.server.io.ResponseWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseWriterTest {

    private ResponseWriter _writer;
    private ByteArrayOutputStream _out;

    @Before
    public void setup() {

        _out = new ByteArrayOutputStream();

    }

    @Test
    public void shouldWriteResponse() throws IOException {

        new ResponseWriter( _out, "response" ).writeResponse();
        Assert.assertEquals( "response\n", new String( _out.toByteArray() ) );

    }

    @Test
    public void shouldWriteErrorResponse() throws IOException {

        new ResponseWriter( _out, new Exception( "an error occurred" ) ).writeResponse();
        Assert.assertEquals( "error:an error occurred\n", new String( _out.toByteArray() ) );

    }

    @Test(expected = IllegalArgumentException.class)
    public void errorForNullResponse() throws IOException {

        new ResponseWriter( _out, ( String ) null ).writeResponse();

    }

    @Test(expected = IllegalArgumentException.class)
    public void errorForNullOutputStream() throws IOException {

        new ResponseWriter( null, "response" ).writeResponse();

    }

    @Test(expected = IllegalArgumentException.class)
    public void errorForNullOutputStreamAndResponse() throws IOException {

        new ResponseWriter( null, ( String ) null ).writeResponse();

    }

}
