package tads.lpo.rh;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection getConnection();

    public static ConnectionFactory getInstance() {
        return ConnectionFactorySingleton.getInstance();
    }
}
