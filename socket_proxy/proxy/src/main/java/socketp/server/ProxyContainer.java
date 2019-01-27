package socketp.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Container class for the proxy accepting incoming requests and passing them
 * off to the destination service.
 */
public class ProxyContainer {
    /**
     * Default port number the server is accepting incoming connections on.
     */
    private static final int DEFAULT_PORT = 7273;
    /**
     * Default maximum number of connections to accept at the same time.
     */
    private static final int DEFAULT_MAX_CONNECTIONS = 3;
    /**
     * The length of time to sleep between checking that all hooks have
     * completed on program exit.
     */
    private static final int SHUTDOWN_THREAD_SLEEP_MS = 200;

    /**
     * The port number the server is accepting incoming connections on.
     */
    private final int portNumber;
    /**
     * The maximum number of connections to accept at the same time.
     */
    private final int maxConnections;
    /**
     * Hooks to register for execution on program exit.
     */
    private final Runnable[] shutdownHooks;
    /**
     * Executor service to run threads and service requests.
     */
    private ExecutorService executorService;

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ProxyContainer.class);

    /**
     * Creates a default container, using the default port (7273) and max.
     * connections (3).
     */
    public ProxyContainer() {
        this(DEFAULT_PORT);
    }

    /**
     * Create a container using a specific port.
     *
     * @param portNumberArg the port number to listen on
     */
    public ProxyContainer(final int portNumberArg) {
        this(portNumberArg, DEFAULT_MAX_CONNECTIONS);
    }

    /**
     * Create a container using a specific port, allowing a specific number of
     * connections.
     *
     * @param portNumberArg     the port number ot listen on
     * @param maxConnectionsArg the max number of threads that can process at
     *                          a time
     */
    public ProxyContainer(
        final int portNumberArg,
        final int maxConnectionsArg
    ) {
        this(portNumberArg, maxConnectionsArg, new Runnable[0]);
    }

    /**
     * Create a container using a specific port, allowing a specific number of
     * connections.
     *
     * @param portNumberArg     the port number ot listen on
     * @param maxConnectionsArg the max number of threads that can process at
     *                          a time
     * @param shutdownHookArgs  Runnables to execute when the proxy shuts down
     */
    public ProxyContainer(
        final int portNumberArg,
        final int maxConnectionsArg,
        final Runnable... shutdownHookArgs
    ) {
        this.portNumber = portNumberArg;
        maxConnections = maxConnectionsArg;
        shutdownHooks = shutdownHookArgs;
        init();
    }

    /**
     * Initialise the application container, create an {@link ExecutorService}
     * to process requests and register the shutdown hook to shutdown the
     * application container when the program exits.
     */
    private void init() {
        executorService = Executors.newFixedThreadPool(maxConnections);

        if (shutdownHooks != null) {
            for (Runnable additionalShutdownHook : shutdownHooks) {
                Runtime.getRuntime().addShutdownHook(
                    new Thread(additionalShutdownHook)
                );
            }
        }

        Runtime.getRuntime().addShutdownHook(
            new Thread(new ContainerShutdownHook(this))
        );
    }

    /**
     * Start the proxy container listening for incoming connections.
     */
    public final void start() {
        ServerSocket serverSocket = createSocketServer();

        LOGGER.info(
            "Proxy started, listening for clients on port ["
                    + portNumber + "]."
        );

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ProxyThread(clientSocket));
            } catch (Exception e) {
                LOGGER.warn(
                    "Exception encountered on accept: " + e.getMessage()
                );
            }
        }
    }

    /**
     * Shutdown the proxy container, stops accepting incoming connections.
     */
    public final void shutDown() {
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(SHUTDOWN_THREAD_SLEEP_MS);
            } catch (InterruptedException e) {
                throw new Error(e);
            }
        }

        LOGGER.info("Proxy shutdown.");
    }

    /**
     * Create the {@link ServerSocket} on which to accept connections.
     *
     * @return the created {@link ServerSocket}.
     */
    private ServerSocket createSocketServer() {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ioe) {
            throw new Error(ioe);
        }

        return serverSocket;
    }
}


/**
 * A {@link Runnable} to shutdown the proxy application container on program
 * exit.
 */
final class ContainerShutdownHook implements Runnable {
    /**
     * Internal reference to the container to shutdown.
     */
    private final ProxyContainer container;

    /**
     * Create a hook to shut down the proxy application container on program
     * exit.
     *
     * @param containerToShutdown the container to shutdown.
     */
    ContainerShutdownHook(final ProxyContainer containerToShutdown) {
        this.container = containerToShutdown;
    }

    /**
     * Shutdown the container.
     */
    public void run() {
        container.shutDown();
    }
}
