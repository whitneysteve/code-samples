package socketp.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Container class for the server accepting incoming requests and passing
 * them off to processing threads.
 */
public class ServerContainer {
    /**
     * Default port number the server is accepting incoming connections on.
     */
    private static final int DEFAULT_PORT = 7272;
    /**
     * Default maximum number of connections to accept at the same time.
     */
    private static final int DEFAULT_MAX_CONNECTIONS = 3;
    /**
     * The length of time to sleep between checking that all hooks have
     * completed on program exit.
     */
    private static final int THREAD_SLEEP_MILLIS = 200;

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
    private static final Logger LOGGER =
        Logger.getLogger(ServerContainer.class);

    /**
     * Creates a default container, using the default port (7272) and max.
     * connections (3).
     */
    public ServerContainer() {
        this(DEFAULT_PORT);
    }

    /**
     * Create a container using a specific port.
     *
     * @param portNumberArg the port number to listen on
     */
    public ServerContainer(final int portNumberArg) {
        this(portNumberArg, DEFAULT_MAX_CONNECTIONS);
    }

    /**
     * Create a container using a specific port, allowing a specific number
     * of connections.
     *
     * @param portNumberArg     the port number ot listen on
     * @param maxConnectionsArg the max number of threads that can process at
     *                          a time
     */
    public ServerContainer(
        final int portNumberArg,
        final int maxConnectionsArg
    ) {
        this(portNumberArg, maxConnectionsArg, new Runnable[0]);
    }

    /**
     * Create a container using a specific port, allowing a specific number
     * of connections.
     *
     * @param portNumberArg     the port number ot listen on
     * @param maxConnectionsArg the max number of threads that can process at
     *                          a time
     * @param shutdownHookArgs  Runnables to execute when the server shuts down
     */
    public ServerContainer(
        final int portNumberArg,
        final int maxConnectionsArg,
        final Runnable... shutdownHookArgs
    ) {
        this.portNumber = portNumberArg;
        this.maxConnections = maxConnectionsArg;
        this.shutdownHooks = shutdownHookArgs;
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

        Runtime.getRuntime().addShutdownHook(new Thread(
            new ContainerShutdownHook(this)
        ));
    }

    /**
     * Start the server container listening for incoming connections.
     */
    public final void start() {
        ServerSocket serverSocket = createSocketServer();

        LOGGER.info(
            "Server started, listening for clients on port ["
                + portNumber + "]."
        );

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ServerThread(clientSocket));
            } catch (Exception e) {
                LOGGER.warn(
                    "Exception encountered on accept: " + e.getMessage()
                );
            }
        }
    }

    /**
     * Shutdown the server container, stops accepting incoming connections.
     */
    public final void shutDown() {
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(THREAD_SLEEP_MILLIS);
            } catch (InterruptedException e) {
                throw new Error(e);
            }
        }

        LOGGER.info("Server shutdown.");
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
class ContainerShutdownHook implements Runnable {
    /**
     * Internal reference to the container to shutdown.
     */
    private final ServerContainer container;

    /**
     * Create a hook to shut down the proxy application container on program
     * exit.
     *
     * @param containerArg the container to shutdown.
     */
    ContainerShutdownHook(final ServerContainer containerArg) {
        this.container = containerArg;
    }

    /**
     * Shutdown the container.
     */
    public void run() {
        container.shutDown();
    }
}
