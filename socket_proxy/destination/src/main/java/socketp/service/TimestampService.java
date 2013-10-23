package socketp.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A service that timestamps a String with the current date.
 */
public class TimestampService {

    /**
     * Timestamps a message with the current date.
     *
     * @param message the message the timestamp
     * @return the timestamped message in the format <date>:<message>
     */
    public String timestamp( String message ) {

        StringBuffer timestampedMessage = new StringBuffer();

        timestampedMessage.append( SimpleDateFormat.getDateInstance().format( new Date() ) );
        timestampedMessage.append( ":" );

        if( message != null ) {

            timestampedMessage.append( message );

        }

        return timestampedMessage.toString();

    }

}
