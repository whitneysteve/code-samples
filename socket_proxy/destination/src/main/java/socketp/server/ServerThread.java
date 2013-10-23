package socketp.server;

import socketp.server.io.RequestParser;
import socketp.server.io.ResponseWriter;
import socketp.service.TimestampService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * A Runnable implemntation to provide server operations.
 */
public class ServerThread implements Runnable {

    private final Socket _clientSocket;

    private static final Logger _logger = Logger.getLogger( ServerThread.class );

    /**
     * Creates a new thread to perform an operation.
     *
     * @param clientSocket the socket to process input from and output the result to.
     */
    public ServerThread( final Socket clientSocket ) {

        _clientSocket = clientSocket;

    }

    public void run() {

        OutputStream out = null;

        try {

            InputStream in = _clientSocket.getInputStream();
            out = _clientSocket.getOutputStream();

            String request = new RequestParser().parse( in );
            String response = new TimestampService().timestamp( request );
            new ResponseWriter( out, response ).writeResponse();

            closeOutput();

        } catch( Exception e ) {

            _logger.error( "An error occurred processing the request", e );

            try {

                new ResponseWriter( out, e ).writeResponse();

            } catch( IOException e1 ) {

                _logger.error( "Error writing error response", e1 );

            }

        } finally {

            try {

                closeSocket();

            } catch( IOException e ) {

                _logger.error( "Error closing socket", e );

            }
        }
    }

    private void closeSocket() throws IOException {

        if( _logger.isTraceEnabled() ) {

            _logger.trace( "Closing socket" );

        }

        if( _clientSocket != null && !_clientSocket.isInputShutdown() && !_clientSocket.isClosed() ) {

            _clientSocket.shutdownInput();

        }

        if( _clientSocket != null && !_clientSocket.isOutputShutdown() && !_clientSocket.isClosed() ) {

            _clientSocket.shutdownOutput();

        }

        if( _clientSocket != null && !_clientSocket.isClosed() ) {

            _clientSocket.close();

        }

        if( _clientSocket != null && !_clientSocket.isInputShutdown() ) {

            _logger.error( "Input not shutdown" );

        }

        if( _clientSocket != null && !_clientSocket.isOutputShutdown() ) {

            _logger.error( "Output not shutdown" );

        }

        if( _clientSocket != null && !_clientSocket.isClosed() ) {

            _logger.error( "Socket not shutdown" );

        }

    }

    private void closeOutput() throws IOException {

        if( _logger.isTraceEnabled() ) {

            _logger.trace( "Closing socket output" );

        }

        if( _clientSocket != null && !_clientSocket.isOutputShutdown() && !_clientSocket.isClosed() ) {

            _clientSocket.shutdownOutput();

        }

    }

}
