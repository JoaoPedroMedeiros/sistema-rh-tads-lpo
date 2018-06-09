package tads.lpo.rh.gui._common;

import tads.lpo.rh.bean.BaseBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ModalGenericoPanel<T extends BaseBean, E extends CadastroGenericoEvents<T>> extends JPanel {

    private Integer id;

    private E modalEvents;

    public ModalGenericoPanel(T bean, E events) {
        this(events);

        if (bean == null)
            throw new NullPointerException();

        this.id = bean.getId();
        fillFields(bean);
    }

    public ModalGenericoPanel(E events) {
        this.modalEvents = events;

        setLayout(new BorderLayout());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    if (validateFields()) {

                        if (ModalGenericoPanel.this.id == null) {
                            getEvents().novo(generateBean());
                        } else {
                            T bean = generateBean();
                            bean.setId(ModalGenericoPanel.this.id);

                            getEvents().editar(bean);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha a todos os campos!");
                    }
                }
                catch (ValidationException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });

        add(initializeFields(), "Center");
        add(btnSalvar, "South");
    }

    protected E getEvents() {
        return modalEvents;
    }

    protected abstract T generateBean();

    protected abstract boolean validateFields();

    protected abstract Component initializeFields();

    protected abstract void fillFields(T bean);

    protected Integer getId() {
        return id;
    }
}
