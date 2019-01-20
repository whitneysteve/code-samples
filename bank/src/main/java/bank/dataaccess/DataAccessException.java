package bank.dataaccess;

/**
 * Exception to indicate there was a problem in the data access layer.
 */
public class DataAccessException extends Exception {
    /**
     * Create an {@link Exception} without a message or cause.
     */
    public DataAccessException() {
    }

    /**
     * Create an {@link Exception} without a cause.
     *
     * @param message a message explaining why this {@link Exception} occurred.
     */
    public DataAccessException(final String message) {
        super(message);
    }

    /**
     * Create an {@link Exception} with a message and cause.
     *
     * @param message a message explaining why this {@link Exception} occurred.
     * @param cause the cause of this {@link Exception}.
     */
    public DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Wrap an {@link Exception} in a data access exception.
     *
     * @param cause the cause of this {@link Exception}.
     */
    public DataAccessException(final Throwable cause) {
        super(cause);
    }
}


