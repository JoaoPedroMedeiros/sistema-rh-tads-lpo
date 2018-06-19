package tads.lpo.rh.gui.login;

import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.dao.FuncionarioDAO;
import tads.lpo.rh.dao.SistemaDAO;
import tads.lpo.rh.gui._common.ErroFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginFrame extends JFrame {

    private ActionListener autenticacaoListener;

    private JTextField userText;

    private JTextField passwordText;

    private JComboBox<SistemaBean> sistemaComboBox;

    public LoginFrame() {
        super();

        try {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Container panel = getContentPane(); //container

            panel.setLayout(null);

            setSize(new Dimension(280, 170));

            JLabel userLabel = new JLabel("Usuário");
            userLabel.setBounds(10, 10, 80, 25);
            panel.add(userLabel);

            userText = new JTextField(20);
            userText.setBounds(100, 10, 160, 25);
            panel.add(userText);

            JLabel passwordLabel = new JLabel("Senha");
            passwordLabel.setBounds(10, 40, 80, 25);
            panel.add(passwordLabel);

            passwordText = new JPasswordField(20);
            passwordText.setBounds(100, 40, 160, 25);
            panel.add(passwordText);

            JLabel sistemaLabel = new JLabel("Sistema");
            sistemaLabel.setBounds(10, 70, 160, 25);
            panel.add(sistemaLabel);

            sistemaComboBox = new JComboBox<>();
            carregarSistemas().forEach(sistema -> sistemaComboBox.addItem(sistema));
            sistemaComboBox.setBounds(100, 70, 160, 25);
            panel.add(sistemaComboBox);

            JButton loginButton = new JButton("Entrar");
            loginButton.setBounds(160, 100, 100, 25);
            panel.add(loginButton);

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    actionLogin();
                }
            });

        } catch (SQLException e) {
            ErroFrame.exibirErro(e);
        }
    }

    public void centralizar() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    private List<SistemaBean> carregarSistemas() throws SQLException {
        return new SistemaDAO().listarTodos(null);
    }

    private boolean autenticar(SistemaBean sistema, String usuario, String senha) throws SQLException {
        Autenticavel autenticavel = new FuncionarioDAO().buscarPorCPF(usuario);

        if (autenticavel != null)
            return autenticavel.autenticar(sistema, usuario, senha);

        return false;
    }

    private void actionLogin() {
        if (userText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Preencha o campo de usuário!");
            return;
        }

        if (passwordText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Preencha a senha!");
            return;
        }

        if (sistemaComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,"Selecione um sistema!");
            return;
        }


        SistemaBean sistema = (SistemaBean) sistemaComboBox.getSelectedItem();

        try {
            if (autenticar(sistema, userText.getText(), passwordText.getText())) {
                this.autenticacaoListener.actionPerformed(new ActionEvent(this, 1, ""));

            } else {
                JOptionPane.showMessageDialog(this, "Você não tem acesso ao sistema " + sistema + " ou o usuário e a senha estão inválidos");
            }
        }
        catch (SQLException e) {
            ErroFrame.exibirErro(e);
        }
    }

    public ActionListener getAutenticacaoListener() {
        return autenticacaoListener;
    }

    public void setAutenticacaoListener(ActionListener autenticacaoListener) {
        this.autenticacaoListener = autenticacaoListener;
    }
}
