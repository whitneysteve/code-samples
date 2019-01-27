package socketp.client;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A client for the timestamp server.
 */
public class TimestampClient {
    private final int portNumber;
    private final String hostName;

    /**
     * Creates a timestamp client pointing to a timestamp server running on a
     * given address.
     *
     * @param hostNameArg   the hostname of the server to connect to
     * @param portNumberArg the port number of the server to connect to
     */
    public TimestampClient(final String hostNameArg, final int portNumberArg) {
        this.hostName = hostNameArg;
        this.portNumber = portNumberArg;
    }

    /**
     * Timestamps a message.
     *
     * @param message the message to timestamp
     * @return the timestammped message
     */
    public String timestamp(final String message) {
        Socket socket = null;
        OutputStream out = null;

        try {
            socket = createSocket();
            out = openOutputStream(socket);

            writeBody(message, out);
            finishOutput(socket);

            InputStream in = openInputStream(socket);

            return new ClientResponseHandler().handleResponse(in);
        } catch (IOException ignored) {
            // Ignored
        } finally {
            Exception e = null;

            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignored) {
                // Ignored
            }

            try {
                if (socket != null) {
                    socket.close();
                }

            } catch (IOException ignored) {
                // Ignored
            }
        }

        return null;
    }

    private InputStream openInputStream(final Socket socket)
        throws IOException {
        return socket.getInputStream();
    }

    private void finishOutput(final Socket socket) {
        if (!socket.isOutputShutdown() && !socket.isClosed()) {
            try {
                socket.shutdownOutput();
            } catch (IOException ignored) {
                // Ignored
            }
        }
    }

    private void writeBody(final String body, final OutputStream out)
        throws IOException {
        IOUtils.write(body, out);
    }

    private OutputStream openOutputStream(final Socket socket)
        throws IOException {
        return socket.getOutputStream();
    }

    private Socket createSocket() {
        try {
            return new Socket(hostName, portNumber);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Cannot contact host.", e);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                "Error connecting to server.", e
            );
        }
    }
}