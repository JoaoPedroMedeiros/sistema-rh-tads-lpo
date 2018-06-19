package tads.lpo.rh;

public class ConnectionFactorySingleton {

    private static ConnectionFactory connectionFactory;

    public static ConnectionFactory getInstance() {
        return connectionFactory;
    }

    public static void setInstance(ConnectionFactory connectionFactory) {
        ConnectionFactorySingleton.connectionFactory = connectionFactory;
    }
}
