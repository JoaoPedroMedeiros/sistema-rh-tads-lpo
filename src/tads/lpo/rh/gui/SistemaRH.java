package tads.lpo.rh.gui;

import tads.lpo.rh.gui.login.LoginFrame;
import tads.lpo.rh.gui.manutencao.cargo.CadastroCargoPanel;
import tads.lpo.rh.gui.manutencao.perfil.CadastroPerfilPanel;
import tads.lpo.rh.gui.manutencao.sistema.CadastroSistemaPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SistemaRH {

    private CadastroSistemaPanel panelCadastroSistema;

    private CadastroCargoPanel panelCadastroCargo;

    private CadastroPerfilPanel panelCadastroPerfil;

    private MDIFrame frameMDI;

    public void iniciar() {
        LoginFrame loginFrame;
        loginFrame = new LoginFrame();
        loginFrame.setAutenticacaoListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarMDI();

                loginFrame.setVisible(false);
                loginFrame.dispose();
            }
        });

        loginFrame.setVisible(true);
        loginFrame.centralizar();
    }

    private void iniciarMDI() {
        frameMDI = new MDIFrame();

        frameMDI.setVisible(true);
        frameMDI.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }




}
