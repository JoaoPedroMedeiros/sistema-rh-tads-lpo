package tads.lpo.rh;

import tads.lpo.rh.bd.HSQLConnectionFactory;
import tads.lpo.rh.bd.MySQLConnectionFactory;
import tads.lpo.rh.gui.MDIFrame;
import tads.lpo.rh.gui.login.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Application {

    public static void main(String[] args) {
        initializeConnectionFactory();

        new Application().start();
    }

    private void start() {
        LoginFrame loginFrame;
        loginFrame = new LoginFrame();
        loginFrame.setAutenticacaoListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MDIFrame frameMDI = new MDIFrame();

                frameMDI.setVisible(true);
                frameMDI.setExtendedState(JFrame.MAXIMIZED_BOTH);

                loginFrame.setVisible(false);
                loginFrame.dispose();
            }
        });

        loginFrame.setVisible(true);
        loginFrame.centralizar();
    }

    private static void initializeConnectionFactory() {
        try {
            Properties properties = new Properties();
            properties.load(Application.class.getResourceAsStream("bd/bd.properties"));

            String dbType = properties.getProperty("db.type");

            if ("HSQL".equals(dbType)) {
                String dbUser = properties.getProperty("db.hsql.user");
                String dbPwd  = properties.getProperty("db.hsql.pwd");
                String dbFile = properties.getProperty("db.hsql.file");

                if (dbUser == null || dbPwd == null || dbFile == null)
                    throw new IOException("As propriedades db.hsql.user, db.hsql.pwd, db.hsql.file precisam ser preenchidas");

                ConnectionFactorySingleton.setInstance(new HSQLConnectionFactory(dbUser, dbPwd, new File(dbFile)));
            }
            else if ("MySQL".equals(dbType)) {
                String dbUser = properties.getProperty("db.mysql.user");
                String dbPwd  = properties.getProperty("db.mysql.pwd");
                String dbSchema = properties.getProperty("db.mysql.schema");

                if (dbUser == null || dbPwd == null || dbSchema == null)
                    throw new IOException("As propriedades db.mysql.user, db.mysql.pwd, db.mysql.schema precisam ser preenchidas");

                ConnectionFactorySingleton.setInstance(new MySQLConnectionFactory(dbUser, dbPwd, dbSchema));
            }
            else {
                throw new IOException("A propriedade db.type não está configurada corretamente");
            }
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
