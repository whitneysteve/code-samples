package socketp.client;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Handles the response from the timestamp server.
 */
public class ClientResponseHandler {

    private static final Logger _logger = Logger.getLogger( ClientResponseHandler.class );

    /**
     * Read and parse the response from the timestamp server.
     *
     * @param in the InputStream from the timestamp server
     * @return the parsed timetamped message
     * @throws java.io.IOException
     */
    public String handleResponse( final InputStream in ) throws IOException {

        if( in == null ) {

            _logger.warn( "Attepmted to parse null input stream" );
            throw new IllegalArgumentException( "Attepmted to parse null input stream" );

        }

        List list = IOUtils.readLines( in );

        if( list == null || list.size() < 1 ) {

            _logger.warn( "No input to read" );

            return null;

        } else {

            String timestampedMessage = ( String ) list.get( 0 );

            if( timestampedMessage.startsWith( "error:" ) ) {

                if( _logger.isTraceEnabled() ) {

                    _logger.trace( "Processed error response: " + timestampedMessage );

                }

                throw new RuntimeException( timestampedMessage );

            } else {

                if( _logger.isTraceEnabled() ) {

                    _logger.trace( "Processed response: " + timestampedMessage );

                }

                return timestampedMessage;

            }

        }

    }

}