package socketp.server;

import socketp.client.TimestampClient;
import socketp.server.io.RequestParser;
import socketp.server.io.ResponseWriter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * A Runnable implementation to process client requests.
 */
public class ProxyThread implements Runnable {
    /**
     * Default host name on which to connect to the proxied destination.
     */
    private static final String DEFAULT_DESTINATION_HOST = "localhost";
    /**
     * Default port number on which to connect to the proxied destination.
     */
    private static final int DEFAULT_DESTINATION_PORT = 7272;

    /**
     * Reference to the {@link Socket} the client is connected and waiting
     * for a response on.
     */
    private final Socket clientSocket;

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ProxyThread.class);

    /**
     * Creates a new thread to perform an operation.
     *
     * @param clientSocketArg the socket to process input from and output the
     *                        result to.
     */
    ProxyThread(final Socket clientSocketArg) {
        this.clientSocket = clientSocketArg;
    }

    /**
     * Run the thread to process the client request.
     */
    public final void run() {
        OutputStream out = null;

        try {
            InputStream in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();

            String request = new RequestParser().parse(in);

            TimestampClient client = retrieveTimestampClient();

            String response = client.timestamp(request);
            new ResponseWriter(out, response).writeResponse();

            closeOutput();
        } catch (Exception e) {
            LOGGER.error("An error occurred processing the request", e);
            try {
                new ResponseWriter(out, e).writeResponse();
            } catch (IOException e1) {
                LOGGER.error("Error writing error response", e1);
            }
        } finally {
            try {
                closeSocket();
            } catch (IOException e) {
                LOGGER.error("Error closing socket", e);
            }
        }
    }

    /**
     * Build a client to the destination.
     *
     * @return {@link TimestampClient} pointing to the configured proxied
     * destination.
     */
    private TimestampClient retrieveTimestampClient() {
        String hostname = System.getProperty("proxy.destination.host");
        String portStr = System.getProperty("proxy.destination.port");
        Integer port = null;

        if (hostname == null || hostname.trim().length() < 1) {
            hostname = DEFAULT_DESTINATION_HOST;
        }

        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException ignored) {
            LOGGER.warn(portStr + " is not a valid port");
        }

        if (port == null) {
            port = DEFAULT_DESTINATION_PORT;
        }

        LOGGER.info(
            "Proxy is using destination host:" + hostname + " and port:" + port
        );

        return new TimestampClient(hostname, port);
    }

    /**
     * Close the client {@link Socket}.
     *
     * @throws IOException if there is an issue closing the connection to the
     * client.
     */
    private void closeSocket() throws IOException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Closing socket");
        }

        if (
            clientSocket != null
                    && !clientSocket.isInputShutdown()
                    && !clientSocket.isClosed()
        ) {
            clientSocket.shutdownInput();
        }

        if (
            clientSocket != null
                    && !clientSocket.isOutputShutdown()
                    && !clientSocket.isClosed()
        ) {
            clientSocket.shutdownOutput();
        }

        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }

        if (clientSocket != null && !clientSocket.isInputShutdown()) {
            LOGGER.error("Input not shutdown");
        }

        if (clientSocket != null && !clientSocket.isOutputShutdown()) {
            LOGGER.error("Output not shutdown");
        }

        if (clientSocket != null && !clientSocket.isClosed()) {
            LOGGER.error("Socket not shutdown");
        }
    }

    /**
     * Close the output {@link Socket}.
     *
     * @throws IOException if there is an issue closing the socket to the
     * client.
     */
    private void closeOutput() throws IOException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Closing socket output");
        }

        if (
            clientSocket != null
                    && !clientSocket.isOutputShutdown()
                    && !clientSocket.isClosed()
        ) {
            clientSocket.shutdownOutput();
        }
    }
}
