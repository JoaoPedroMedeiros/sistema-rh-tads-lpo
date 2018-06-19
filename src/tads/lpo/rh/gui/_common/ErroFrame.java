package tads.lpo.rh.gui._common;

import javax.swing.*;

public class ErroFrame {

    public static void exibirErro(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
