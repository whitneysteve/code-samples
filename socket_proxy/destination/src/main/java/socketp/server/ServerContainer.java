package socketp.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Container class for the server accepting incoming requests and passing them off to processing threads.
 */
public class ServerContainer {

    public static final int DEFAULT_PORT = 7272;
    public static final int DEFAULT_MAX_CONNECTIONS = 3;

    private final int _portNumber;
    private final int _maxConnections;
    private final Runnable[] _shutdownHooks;
    private ExecutorService _executorService;


    private static final Logger _logger = Logger.getLogger( ServerContainer.class );

    /**
     * Creates a default container, using the default port (7272) and max. connections (3).
     */
    public ServerContainer() {

        this( DEFAULT_PORT );

    }

    /**
     * Create a container using a specific port.
     *
     * @param portNumber the port number to listen on
     */
    public ServerContainer( final int portNumber ) {

        this( portNumber, DEFAULT_MAX_CONNECTIONS );

    }

    /**
     * Create a container using a specific port, allowing a specific number of connections.
     *
     * @param portNumber     the port number ot listen on
     * @param maxConnections the max number of threads that can process at a time
     */
    public ServerContainer( final int portNumber, final int maxConnections ) {

        this( portNumber, maxConnections, new Runnable[ 0 ] );

    }

    /**
     * * Create a container using a specific port, allowing a specific number of connections.
     *
     * @param portNumber     the port number ot listen on
     * @param maxConnections the max number of threads that can process at a time
     * @param shutdownHooks  Runnables to execute when the server shuts down
     */
    public ServerContainer( final int portNumber, final int maxConnections, final Runnable... shutdownHooks ) {

        _portNumber = portNumber;
        _maxConnections = maxConnections;
        _shutdownHooks = shutdownHooks;
        init();

    }

    private void init() {

        _executorService = Executors.newFixedThreadPool( _maxConnections );

        if( _shutdownHooks != null ) {

            for( Runnable additionalShutdownHook : _shutdownHooks ) {

                Runtime.getRuntime().addShutdownHook( new Thread( additionalShutdownHook ) );

            }

        }

        Runtime.getRuntime().addShutdownHook( new Thread( new ContainerShutdownHook( this ) ) );

    }

    /**
     * Start the server container listening for incoming connections.
     */
    public void start() {

        ServerSocket serverSocket = createSocketServer();

        _logger.info( "Server started, listening for clients on port [" + _portNumber + "]." );

        while( true ) {

            try {

                Socket clientSocket = serverSocket.accept();
                _executorService.submit( new ServerThread( clientSocket ) );

            } catch( Exception e ) {

                _logger.warn( "Exception encountered on accept: " + e.getMessage() );

            }

        }

    }

    /**
     * Shutdown the server container, stops accepting incoming connections.
     */
    public void shutDown() {

        _executorService.shutdown();

        while( !_executorService.isTerminated() ) {

            try {

                Thread.sleep( 200 );

            } catch( InterruptedException e ) {

                throw new Error( e );

            }

        }

        _logger.info( "Server shutdown." );

    }

    private ServerSocket createSocketServer() {

        ServerSocket serverSocket;

        try {

            serverSocket = new ServerSocket( _portNumber );

        } catch( IOException ioe ) {

            throw new Error( ioe );

        }

        return serverSocket;

    }

}

class ContainerShutdownHook implements Runnable {

    private final ServerContainer _container;

    ContainerShutdownHook( ServerContainer container ) {

        _container = container;
    }

    public void run() {

        _container.shutDown();
    }
}
