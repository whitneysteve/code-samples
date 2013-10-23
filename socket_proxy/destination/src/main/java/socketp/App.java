package socketp;

import socketp.server.ServerContainer;

/**
 * Driver class for timestamp service.
 */
public class App {

    public static void main( String[] args ) {

        new ServerContainer().start();

    }

}
