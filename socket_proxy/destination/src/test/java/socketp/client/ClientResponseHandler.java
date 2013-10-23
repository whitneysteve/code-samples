package socketp.client;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Handles the response from the timestamp server.
 */
public class ClientResponseHandler {

    /**
     * Read and parse the response from the timestamp server.
     *
     * @param in the InputStream from the timestamp server
     * @return the parsed timetamped message
     * @throws IOException
     */
    public String handleResponse( final InputStream in ) throws IOException {

        if( in == null ) {

            return null;

        }

        List list = IOUtils.readLines( in );

        if( list == null || list.size() < 1 ) {

            return null;

        } else {

            String timestampedMessage = ( String ) list.get( 0 );

            if( timestampedMessage.startsWith( "error:" ) ) {

                throw new RuntimeException( timestampedMessage );

            } else {

                return timestampedMessage;

            }

        }

    }

}