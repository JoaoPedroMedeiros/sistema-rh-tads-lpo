package tads.lpo.rh.gui.manutencao.departamento;

import tads.lpo.rh.bean.DepartamentoBean;
import tads.lpo.rh.gui._common.CadastroGenericoModal;

import javax.swing.*;
import java.awt.*;

public class CadastroDepartamentoModal extends CadastroGenericoModal<DepartamentoBean> {

    public static final Dimension dimensions = new Dimension(400, 140);

    private JTextField nameText;

    public CadastroDepartamentoModal(DepartamentoBean bean) {
        super(bean);
    }

    public CadastroDepartamentoModal() {
        super();
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
    protected DepartamentoBean generateBean() {
        return new DepartamentoBean(null, nameText.getText());
    }

    @Override
    protected boolean validateFields() {
        return !nameText.getText().isEmpty();
    }

    @Override
    protected void fillFields(DepartamentoBean bean) {
        nameText.setText(bean.getNome());
    }
}
