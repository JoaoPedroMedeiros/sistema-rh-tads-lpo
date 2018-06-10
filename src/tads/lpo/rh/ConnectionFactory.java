package tads.lpo.rh;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection getConnection();

    static ConnectionFactory connectionFactory = null;

    public static ConnectionFactory getInstance() {
        return connectionFactory;
    }

    static void setInstance(ConnectionFactory connectionFactory) {
        connectionFactory = connectionFactory;
    }
}
