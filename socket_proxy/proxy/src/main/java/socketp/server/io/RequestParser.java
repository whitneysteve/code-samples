package socketp.server.io;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Parses the incoming request from the client.
 */
public class RequestParser {
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(RequestParser.class);

    /**
     * Parses the first line of input.
     *
     * @param in InputStream to parse from.
     * @return the first line of input from the InputStream
     * @throws java.io.IOException if there is an error reading the request
     * body from the client's {@link InputStream}.
     */
    public final String parse(final InputStream in) throws IOException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Parsing request");
        }

        if (in == null) {
            LOGGER.warn("Attempted to parse request from null input stream");
            throw new IllegalArgumentException(
                "Attempted to parse request from null input stream"
            );
        }

        String line = readLine(in);

        if (line != null && line.trim().length() > 0) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Received request:" + line);
            }

            return line;
        } else {
            LOGGER.warn("No input received");
            throw new IllegalArgumentException("No input received");
        }
    }

    /**
     * Read a line from the {@link InputStream}.
     *
     * @param in the {@link InputStream} to read from.
     * @return byte data read from the {@link InputStream} converted to
     *         a {@link String}.
     * @throws IOException if there is an issue reading from
     *         the {@link InputStream}.
     */
    private String readLine(final InputStream in) throws IOException {
        byte[] rawData = readRawLine(in);

        if (rawData == null) {
            return null;
        }

        int len = rawData.length;
        int offset = 0;

        if (len > 0) {
            if (rawData[ len - 1 ] == '\n') {
                offset++;
                if (len > 1) {
                    if (rawData[ len - 2 ] == '\r') {
                        offset++;
                    }
                }
            }
        }

        return new String(rawData, 0, len - offset);
    }

    /**
     * Read a raw line from the {@link InputStream}.
     *
     * @param in the {@link InputStream} to read from.
     * @return array of bytes containing the next line.
     * @throws IOException if there is an issue reading from
     *         the {@link InputStream}.
     */
    private byte[] readRawLine(final InputStream in) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int ch;

        while ((ch = in.read()) >= 0) {
            if (ch == '\n') {
                break;
            }

            buf.write(ch);
        }
        return buf.toByteArray();
    }
}
