package socketp;

import socketp.client.TimestampClient;
import socketp.server.ServerContainer;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Integration test for timestamp App.
 */
public class AppTest {

    private static ServerContainer SERVER;
    private static TimestampClient CLIENT;
    private String _todaysDate;

    @BeforeClass
    public static void setupServer() throws InterruptedException {

        new Thread( new Runnable() {

            public void run() {

                // Use a test port
                SERVER = new ServerContainer( 17272 );
                SERVER.start();

            }

        } ).start();

        CLIENT = new TimestampClient( "localhost", 17272 );

        // Wait for the server to come up
        Thread.sleep( 2000 );

    }

    @AfterClass
    public static void shutdownServer() {

        SERVER.shutDown();

    }

    @Before
    public void setupTest() {

        _todaysDate = SimpleDateFormat.getDateInstance().format( new Date() );

    }

    @Test
    public void shouldTimestampMessage() {

        Assert.assertEquals( _todaysDate + ":" + "message", CLIENT.timestamp( "message" ) );

    }

    @Test
    public void errorForEmptyMessage() {

        Exception error = null;

        try {

            CLIENT.timestamp( "" );

        } catch( Exception e ) {

            error = e;

        }

        if( error == null ) {

            Assert.fail( "Expected failure" );

        }

        Assert.assertEquals( "error:No input received", error.getMessage() );

    }

    @Test
    public void errorForNullMessage() {

        Exception error = null;

        try {

            CLIENT.timestamp( null );

        } catch( Exception e ) {

            error = e;

        }

        if( error == null ) {

            Assert.fail( "Expected failure" );

        }

        Assert.assertEquals( "error:No input received", error.getMessage() );

    }

}
