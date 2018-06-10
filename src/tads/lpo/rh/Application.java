package tads.lpo.rh;

import tads.lpo.rh.gui.MDIFrame;
import tads.lpo.rh.gui.login.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {

    public static void main(String[] args) {
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
}
