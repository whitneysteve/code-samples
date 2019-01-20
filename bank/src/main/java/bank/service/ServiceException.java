package bank.service;

/**
 * Very generic {@link Exception} thrown by the service layer, upon error.
 */
public class ServiceException extends Exception {

    /**
     * Create an exception with no message or cause.
     */
    public ServiceException() {
    }

    /**
     * Create an exception with only a message.
     *
     * @param message message to explain this exception.
     */
    public ServiceException(final String message) {
        super(message);
    }

    /**
     * Create an exception with a message and cause.
     *
     * @param message message to explain this exception.
     * @param cause the cause of this exception.
     */
    public ServiceException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }

    /**
     * Wrap a causing exception in a {@link ServiceException}.
     *
     * @param cause the cause of this exception.
     */
    public ServiceException(final Throwable cause) {
        super(cause);
    }
}
