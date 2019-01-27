package socketp;

import socketp.server.ProxyContainer;

/**
 * Main class for the proxy to the timestamp service.
 */
public final class App {
    /**
     * Hidden constructor for utility class.
     */
    private App() {
        // Nothing
    }

    /**
     * The entry method to the program.
     *
     * @param args command line arguments given to the program.
     */
    public static void main(final String[] args) {
        new ProxyContainer().start();
    }
}
