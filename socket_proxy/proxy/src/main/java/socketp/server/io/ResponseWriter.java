package socketp.server.io;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Write a response to the client.
 */
public class ResponseWriter {
    /**
     * The {@link OutputStream} to write to.
     */
    private final OutputStream out;
    /**
     * The result to write to the client.
     */
    private final String result;
    /**
     * An error that occurred that must be written to the client.
     */
    private final Throwable error;

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER =
            Logger.getLogger(ResponseWriter.class);

    /**
     * Creates a ResponseWriter that outputs a String result.
     *
     * @param outArg    the OutputStream to write to
     * @param resultArg the String result to write
     */
    public ResponseWriter(
        final OutputStream outArg,
        final String resultArg
    ) {

        this.out = outArg;
        this.result = resultArg;
        error = null;

    }

    /**
     * Creates a ResponseWriter that outputs an error response.
     *
     * @param outArg   the OutputStream to write to
     * @param errorArg the exception to report to the client.
     */
    public ResponseWriter(
        final OutputStream outArg,
        final Throwable errorArg
    ) {
        this.out = outArg;
        result = null;
        this.error = errorArg;
    }

    /**
     * Write the response.
     *
     * @throws IOException if there is an issue writing to
     *         the {@link OutputStream}
     */
    public final void writeResponse() throws IOException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Writing response");
        }

        if (out == null) {
            LOGGER.warn("Attempted to write a response to null output stream");
            throw new IllegalArgumentException(
                "Attempted to write a response to null output stream"
            );
        }

        if (isError()) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Writing error response");
            }

            writeInternal("error:" + error.getMessage());
        } else {
            writeInternal(result);
        }
    }

    /**
     * Perform the actual write to the {@link OutputStream}.
     *
     * @param output the output that gets sent to the client.
     * @throws IOException if there is an issue writing to
     *         the {@link OutputStream}
     */
    private void writeInternal(final String output) throws IOException {
        if (output == null) {
            LOGGER.warn("Attempted to output a null result");
            throw new IllegalArgumentException(
                "Attempted to output null result"
            );
        }

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Writing result:" + output);
        }

        IOUtils.write(output + "\n", out, Charset.defaultCharset());
    }

    /**
     * Check if this response writer is writing an error to the client.
     *
     * @return true if the response is an error response, false if not.
     */
    private boolean isError() {
        return error != null;
    }
}
