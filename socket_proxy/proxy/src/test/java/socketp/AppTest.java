package socketp;

import socketp.client.TimestampClient;
import socketp.server.ProxyContainer;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Integration test for timestamp proxy.
 */
public class AppTest {
    private static ProxyContainer PROXY;
    private static TimestampClient CLIENT;
    private String todaysDate;

    @BeforeClass
    public static void setupServer()throws InterruptedException {
        new Thread(new Runnable() {

            public void run() {
            // Use a test port, assumes destination server is already running on 7272 (the default)
            PROXY = new ProxyContainer(17273);
            PROXY.start();
            }

        }).start();

        CLIENT = new TimestampClient("localhost", 17273);

        // Wait for the server to come up
        Thread.sleep(2000);
    }

    @AfterClass
    public static void shutdownServer() {
        PROXY.shutDown();
    }

    @Before
    public void setupTest() {
        todaysDate = SimpleDateFormat.getDateInstance().format(new Date());
    }

    @Test
    public void shouldTimestampMessage() {
        Assert.assertEquals(todaysDate + ":" + "message", CLIENT.timestamp("message"));
    }

    @Test
    public void errorForEmptyMessage() {
        Exception error = null;
        try {
            CLIENT.timestamp("");
        } catch (Exception e) {
            error = e;
        }

        if (error == null) {
            Assert.fail("Expected failure");
        }

        Assert.assertEquals("error:No input received", error.getMessage());
    }

    @Test
    public void errorForNullMessage() {
        Exception error = null;

        try {
            CLIENT.timestamp(null);
        } catch (Exception e) {
            error = e;
        }

        if (error == null) {
            Assert.fail("Expected failure");
        }

        Assert.assertEquals("error:No input received", error.getMessage());
    }
}
