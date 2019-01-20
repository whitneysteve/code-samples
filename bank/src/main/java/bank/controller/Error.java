package bank.controller;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * An error model used to send back useful messages to the client, beyond
 * HTTP status codes.
 */
public class Error {

    /**
     * Create an error with a message.
     *
     * @param newMessage the message that explains the error.
     */
    Error(final String newMessage) {
        this.message = newMessage;
    }

    /**
     * The message explaining this error.
     */
    private String message;

    /**
     * @return the message explaining this error.
     */
    public final String getMessage() {
        return message;
    }

    /**
     * @param newMessage the message expalining this error.
     */
    public final void setMessage(final String newMessage) {
        this.message = newMessage;
    }

    /**
     * Convert the error to a JSON representation.
     *
     * @param mapper the {@link ObjectMapper} used to convert the error to JSON.
     * @return the rendered JSON or an empty {@link String} on failure.
     */
    public final String toJson(final ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
