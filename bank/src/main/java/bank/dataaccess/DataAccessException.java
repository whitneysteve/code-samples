package bank.dataaccess;

public class DataAccessException extends Exception {

    public DataAccessException() {

    }

    public DataAccessException( String s ) {

        super( s );

    }

    public DataAccessException( String s, Throwable throwable ) {

        super( s, throwable );

    }

    public DataAccessException( Throwable throwable ) {

        super( throwable );

    }
}


