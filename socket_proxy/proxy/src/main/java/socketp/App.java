package socketp;

import socketp.server.ProxyContainer;

/**
 * Main class for the proxy to the timestamp service.
 */
public class App {

    public static void main( String[] args ) {

        new ProxyContainer().start();

    }

}
