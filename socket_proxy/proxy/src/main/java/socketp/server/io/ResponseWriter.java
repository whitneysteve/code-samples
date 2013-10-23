package socketp.server.io;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Write a response to the client.
 */
public class ResponseWriter {

    private final OutputStream _out;
    private final String _result;
    private final Throwable _error;

    private static final Logger _logger = Logger.getLogger( ResponseWriter.class );

    /**
     * Creates a ResponseWriter that outputs a String result.
     *
     * @param out    the OutputStream to write to
     * @param result the String result to write
     */
    public ResponseWriter( final OutputStream out, String result ) {

        _out = out;
        _result = result;
        _error = null;

    }

    /**
     * Creates a ResponseWriter that outputs an error response.
     *
     * @param out   the OutputStream to write to
     * @param error the exception to report to the client.
     */
    public ResponseWriter( final OutputStream out, Throwable error ) {

        _out = out;
        _result = null;
        _error = error;

    }

    /**
     * Write the response.
     *
     * @throws java.io.IOException
     */
    public void writeResponse() throws IOException {

        if( _logger.isTraceEnabled() ) {

            _logger.trace( "Writing response" );

        }

        if( _out == null ) {

            _logger.warn( "Attempted to write a response to null output stream" );
            throw new IllegalArgumentException( "Attempted to write a response to null output stream" );

        }
        if( isError() ) {

            if( _logger.isTraceEnabled() ) {

                _logger.trace( "Writing error response" );

            }

            writeInternal( "error:" + _error.getMessage() );

        } else {

            writeInternal( _result );

        }

    }

    private void writeInternal( String result ) throws IOException {

        if( result == null ) {

            _logger.warn( "Attempted to output a null result" );
            throw new IllegalArgumentException( "Attempted to output null result" );

        }

        if( _logger.isTraceEnabled() ) {

            _logger.trace( "Writing result:" + result );

        }

        IOUtils.write( result + "\n", _out );

    }

    private boolean isError() {

        return _error != null;

    }

}