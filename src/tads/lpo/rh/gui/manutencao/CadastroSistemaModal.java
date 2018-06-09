package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.gui._common.ModalGenericoPanel;

import javax.swing.*;
import java.awt.*;

public class CadastroSistemaModal extends ModalGenericoPanel<SistemaBean, CadastroSistemaEvents> {

    private JTextField nameText;

    public CadastroSistemaModal(SistemaBean bean, CadastroSistemaEvents events) {
        super(bean, events);
    }

    public CadastroSistemaModal(CadastroSistemaEvents events) {
        super(events);
    }

    @Override
    protected Component initializeFields() {
        JPanel panel = new JPanel();

        BorderLayout layout = new BorderLayout();
        layout.setVgap(20);
        layout.setHgap(10);

        panel.setLayout(layout);

        JPanel espacamentoNorte = new JPanel();
        JPanel espacamentoLeste = new JPanel();
        JPanel espacamentoSul = new JPanel();
        JPanel panelLabels = new JPanel();
        JPanel panelFields = new JPanel();

        panel.add(espacamentoNorte, "North");
        panel.add(espacamentoLeste, "East");
        panel.add(espacamentoSul, "South");
        panel.add(panelLabels, "West");
        panel.add(panelFields, "Center");

        GridLayout labelsLayout = new GridLayout(1, 1);
        GridLayout fieldsLayout = new GridLayout(1, 1);
        labelsLayout.setVgap(10);
        fieldsLayout.setVgap(10);

        panelLabels.setLayout(labelsLayout);
        panelFields.setLayout(fieldsLayout);

        panelLabels.add(new JLabel("   Nome:"));

        nameText = new JTextField();
        panelFields.add(nameText);

        return panel;
    }

    @Override
    protected SistemaBean generateBean() {
        return new SistemaBean(null, nameText.getText());
    }

    @Override
    protected boolean validateFields() {
        return !nameText.getText().isEmpty();
    }

    @Override
    protected void fillFields(SistemaBean bean) {
        nameText.setText(bean.getNome());
    }
}
