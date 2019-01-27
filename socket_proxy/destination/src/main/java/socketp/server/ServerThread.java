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
 * A Runnable implementation to provide server operations.
 */
public class ServerThread implements Runnable {
    /**
     * The {@link Socket} on which we receive requests from the client.
     */
    private final Socket clientSocket;

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ServerThread.class);

    /**
     * Creates a new thread to perform an operation.
     *
     * @param clientSocketArg the socket to process input from and output the
     *                        result to.
     */
    public ServerThread(final Socket clientSocketArg) {
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
            String response = new TimestampService().timestamp(request);
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
