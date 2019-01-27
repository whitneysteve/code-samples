package socketp.client;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/**
 * A client for the timestamp server.
 */
public class TimestampClient {
    /**
     * The host name of the server to contact.
     */
    private String hostName;
    /**
     * The port number to contact on the server.
     */
    private int portNumber;

    /**
     * The Logger for this class.
     */
    private static final Logger LOGGER =
            Logger.getLogger(TimestampClient.class);

    /**
     * Creates a timestamp client pointing to a timestamp server running on a
     * given address.
     *
     * @param providedHostName   the hostname of the server to connect to
     * @param providedPortNumber the port number of the server to connect to
     */
    public TimestampClient(
        final String providedHostName,
        final int providedPortNumber
    ) {
        this.hostName = providedHostName;
        this.portNumber = providedPortNumber;

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(
                "Timestamp client is using hostname: " + hostName
                        + " and port: " + providedPortNumber
            );
        }
    }

    /**
     * Timestamps a message.
     *
     * @param message the message to timestamp
     * @return the timestammped message
     */
    public final String timestamp(final String message) {
        Socket socket = null;
        OutputStream out = null;

        try {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(
                    "Attempting to timestamp message: " + message
                );
            }

            socket = createSocket();
            out = openOutputStream(socket);

            writeBody(message, out);
            finishOutput(socket);

            InputStream in = openInputStream(socket);
            return new ClientResponseHandler().handleResponse(in);
        } catch (IOException ignored) {
            // Ignored.
        } finally {
            Exception e = null;

            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                e = ioe;
            }

            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ioe) {
                e = ioe;
            }

            if (e != null) {
                // Shouldn't throw in a final block, but no real choice here.
                throw new IllegalStateException(
                    "Could not close socket connection correctly",
                    e
                );
            }
        }
        return null;
    }

    /**
     * Open an {@link InputStream} to read from the server.
     *
     * @param socket the {@link Socket} to connect over.
     * @return the created {@link InputStream}.
     * @throws IOException if there is an issue connecting the input stream
     * to there server.
     */
    private InputStream openInputStream(final Socket socket)
            throws IOException {
        return socket.getInputStream();
    }

    /**
     * Finish output on a {@link Socket}.
     *
     * @param socket the {@link Socket} to stop outputting on.
     */
    private void finishOutput(final Socket socket) {
        if (!socket.isOutputShutdown() && !socket.isClosed()) {
            try {
                socket.shutdownOutput();
            } catch (IOException ignored) {
                // Ignored
            }
        }
    }

    /**
     * Write the body of the message.
     *
     * @param body the body to write.
     * @param out the {@link OutputStream} to write the message over.
     * @throws IOException if there is an issue writing over the
     *         {@link OutputStream}
     */
    private void writeBody(final String body, final OutputStream out)
            throws IOException {
        IOUtils.write(body, out, Charset.defaultCharset());
    }

    /**
     * Open an {@link OutputStream} over the {@link Socket}.
     *
     * @param socket the {@link Socket} to open the connection on.
     * @return the {@link OutputStream} opened.
     * @throws IOException if there is an issue opening the output stream.
     */
    private OutputStream openOutputStream(final Socket socket)
            throws IOException {
        return socket.getOutputStream();
    }

    /**
     * Open the {@link Socket}.
     * @return opened {@link Socket} object.
     */
    private Socket createSocket() {
        try {
            return new Socket(hostName, portNumber);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Cannot contact host.", e);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                "Error connecting to server.",
                e
            );
        }
    }
}
