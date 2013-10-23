package socketp.client;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A client for the timestamp server.
 */
public class TimestampClient {

    private final int _portNumber;
    private final String _hostName;

    private static final Logger _logger = Logger.getLogger( TimestampClient.class );

    /**
     * Creates a timestamp client pointing to a timestamp server running on a given address.
     *
     * @param hostName   the hostname of the server to connect to
     * @param portNumber the port number of the server to connect to
     */
    public TimestampClient( final String hostName, final int portNumber ) {

        _hostName = hostName;
        _portNumber = portNumber;

        if( _logger.isTraceEnabled() ) {

            _logger.trace( "Timestamp client is using hostname: " + hostName + " and port: " + portNumber );

        }

    }

    /**
     * Timestamps a message.
     *
     * @param message the message to timestamp
     * @return the timestammped message
     */
    public String timestamp( String message ) {

        Socket socket = null;
        OutputStream out = null;

        try {

            if( _logger.isTraceEnabled() ) {

                _logger.trace( "Attempting to timestamp message: " + message );

            }

            socket = createSocket();
            out = openOutputStream( socket );

            writeBody( message, out );
            finishOutput( socket );

            InputStream in = openInputStream( socket );

            return new ClientResponseHandler().handleResponse( in );

        } catch( IOException e ) {

        } finally {

            Exception e = null;

            try {

                if( out != null ) {

                    out.close();

                }

            } catch( IOException ioe ) {

                e = ioe;

            }

            try {

                if( socket != null ) {

                    socket.close();

                }

            } catch( IOException ioe ) {

                e = ioe;

            }

            if( e != null ) {

                throw new IllegalStateException( "Could not close socket connection correctly", e );

            }

        }

        return null;

    }

    private InputStream openInputStream( final Socket socket ) throws IOException {

        return socket.getInputStream();

    }

    private void finishOutput( final Socket socket ) {

        if( !socket.isOutputShutdown() && !socket.isClosed() ) {

            try {

                socket.shutdownOutput();

            } catch( IOException ignored ) {
            }

        }

    }

    private void writeBody( final String body, final OutputStream out ) throws IOException {

        IOUtils.write( body, out );

    }

    private OutputStream openOutputStream( final Socket socket ) throws IOException {

        return socket.getOutputStream();

    }

    private Socket createSocket() {

        try {

            return new Socket( _hostName, _portNumber );

        } catch( UnknownHostException e ) {

            throw new IllegalArgumentException( "Cannot contact host.", e );

        } catch( IOException e ) {

            throw new IllegalArgumentException( "Error connecting to server.", e );

        }

    }

}