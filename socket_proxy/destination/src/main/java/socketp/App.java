package socketp;

import socketp.server.ServerContainer;

/**
 * Driver class for timestamp service.
 */
public final class App {
    /**
     * Hiding constructor for utility class.
     */
    private App() {
        // Nothing
    }

    /**
     * The entry point into the application.
     *
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        new ServerContainer().start();
    }
}
