package tads.lpo.rh.gui._common;

import tads.lpo.rh.bean.BaseBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public abstract class CadastroGenericoModal<T extends BaseBean> extends JPanel {

    private Integer id;

    private ActionListener savedListener;

    public CadastroGenericoModal(T bean) {
        this();

        if (bean == null)
            throw new NullPointerException();

        this.id = bean.getId();
        fillFields(bean);
    }

    public CadastroGenericoModal() {
        setLayout(new BorderLayout());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    if (validateFields()) {

                        T bean = generateBean();

                        if (CadastroGenericoModal.this.id == null) {
                            savedListener.actionPerformed(new ActionEvent(bean, 1, "novo"));

                        } else {
                            bean.setId(CadastroGenericoModal.this.id);
                            savedListener.actionPerformed(new ActionEvent(bean, 2, "editar"));
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha a todos os campos!");
                    }
                }
                catch (SQLException e) {
                    ErroFrame.exibirErro(e);
                }
                catch (ValidationException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });

        try {
            add(initializeFields(), "Center");

            add(btnSalvar, "South");
        }
        catch (SQLException e) {
            ErroFrame.exibirErro(e);
        }
    }

    protected abstract T generateBean();

    protected abstract boolean validateFields() throws SQLException;

    protected abstract Component initializeFields() throws SQLException;

    protected abstract void fillFields(T bean);

    protected Integer getId() {
        return id;
    }

    public void setSavedListener(ActionListener savedListener) {
        this.savedListener = savedListener;
    }
}
