package socketp.server.io;

import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RequestParserTest {
    @Test
    public void shouldParseLine() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(
            "request".getBytes()
        );
        Assert.assertEquals("request", new RequestParser().parse(in));
    }

    @Test
    public void shouldParseFirstLineOfMany() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(
            "request1\nrequest2".getBytes()
        );
        Assert.assertEquals("request1", new RequestParser().parse(in));
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorForEmptyInput() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        new RequestParser().parse(in);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorForNullInputStream() throws IOException {
        new RequestParser().parse(null);
    }
}
