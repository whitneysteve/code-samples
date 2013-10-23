package socketp.service;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampServiceTest {

    private TimestampService _service;
    private String _todaysDate;

    @Before
    public void setup() {

        _service = new TimestampService();
        _todaysDate = SimpleDateFormat.getDateInstance().format( new Date() );

    }

    @Test
    public void shouldTimestampMessage() {

        Assert.assertEquals( _todaysDate + ":" + "message", _service.timestamp( "message" ) );

    }

    @Test
    public void shouldTimestampEmptyMessage() {

        Assert.assertEquals( _todaysDate + ":", _service.timestamp( "" ) );

    }

    @Test
    public void shouldTimestampNullMessage() {

        Assert.assertEquals( _todaysDate + ":", _service.timestamp( null ) );

    }

}
