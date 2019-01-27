package socketp.client;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Handles the response from the timestamp server.
 */
class ClientResponseHandler {

    /**
     * The Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger(ClientResponseHandler.class);

    /**
     * Read and parse the response from the timestamp server.
     *
     * @param in the InputStream from the timestamp server
     * @return the parsed timetamped message
     * @throws java.io.IOException on error reading response.
     */
    String handleResponse(final InputStream in)throws IOException {
        if (in == null) {
            LOGGER.warn("Attepmted to parse null input stream");
            throw new IllegalArgumentException(
                "Attempted to parse null input stream"
            );
        }

        List list = IOUtils.readLines(in, Charset.defaultCharset());

        if (list == null || list.size() < 1) {
            LOGGER.warn("No input to read");
            return null;
        } else {
            String timestampedMessage = (String) list.get(0);

            if (timestampedMessage.startsWith("error:")) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(
                        "Processed error response: " + timestampedMessage
                    );
                }

                throw new RuntimeException(timestampedMessage);
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Processed response: " + timestampedMessage);
                }

                return timestampedMessage;
            }
        }
    }
}
