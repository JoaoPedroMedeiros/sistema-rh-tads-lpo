package tads.lpo.rh.gui.manutencao.cargo;

import tads.lpo.rh.bean.CargoBean;
import tads.lpo.rh.bean.CargoExecutivoBean;
import tads.lpo.rh.bean.CargoGerencialBean;
import tads.lpo.rh.bean.CargoOperacionalBean;
import tads.lpo.rh.gui._common.CadastroGenericoModal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CadastroCargoModal extends CadastroGenericoModal<CargoBean> {

    public static final Dimension dimensions = new Dimension(400, 400);

    private JComboBox<String> tipoCombobox;

    private JTextField nomeText;
    private JFormattedTextField salarioNivel1Text;
    private JFormattedTextField salarioNivel2Text;
    private JFormattedTextField salarioNivel3Text;
    private JFormattedTextField porcentagemBonificacaoText;

    private JFormattedTextField bonificacaoDepartamentoText;
    private JFormattedTextField bonificacaoPessoaText;
    private JCheckBox acessoCheckbox;

    public CadastroCargoModal(CargoBean bean) {
        super(bean);
    }

    public CadastroCargoModal() {
        super();
    }

    @Override
    protected CargoBean generateBean() {
        CargoBean cargo;

        String tipo = (String) tipoCombobox.getSelectedItem();

        if (tipo.equals("Executivo")) {
            cargo = new CargoExecutivoBean(new BigDecimal(((Number) bonificacaoDepartamentoText.getValue()).doubleValue()));
        }
        else if (tipo.equals("Gerencial")) {
            cargo = new CargoGerencialBean(new BigDecimal(((Number) bonificacaoPessoaText.getValue()).doubleValue()));
        }
        else if (tipo.equals("Operacional")) {
            cargo = new CargoOperacionalBean();
            ((CargoOperacionalBean) cargo).setAcessaSistema(acessoCheckbox.isSelected());
        }
        else
            throw new IllegalArgumentException();

        cargo.setNome(nomeText.getText());
        cargo.setSalarioNivel1(new BigDecimal(((Number) salarioNivel1Text.getValue()).doubleValue()));
        cargo.setSalarioNivel2(new BigDecimal(((Number) salarioNivel2Text.getValue()).doubleValue()));
        cargo.setSalarioNivel3(new BigDecimal(((Number) salarioNivel3Text.getValue()).doubleValue()));

        cargo.setPercentualSalarioBonus(new BigDecimal(((Number) porcentagemBonificacaoText.getValue()).doubleValue()).setScale(1, RoundingMode.HALF_EVEN));

        return cargo;
    }

    @Override
    protected boolean validateFields() {
        try {
            if (tipoCombobox.getSelectedItem() == null)
                return false;
            if (salarioNivel1Text.getValue() == null || ((Number) salarioNivel1Text.getValue()).doubleValue() <= 0d)
                return false;
            if (salarioNivel2Text.getValue() == null || ((Number) salarioNivel2Text.getValue()).doubleValue() <= 0d)
                return false;
            if (salarioNivel3Text.getValue() == null || ((Number) salarioNivel3Text.getValue()).doubleValue() <= 0d)
                return false;


            if (porcentagemBonificacaoText.getValue() == null || ((Number) porcentagemBonificacaoText.getValue()).doubleValue() < 0d)
                return false;
            if (((String) tipoCombobox.getSelectedItem()).equals("Executivo"))
                if (bonificacaoDepartamentoText.getValue() == null || ((Number) bonificacaoDepartamentoText.getValue()).doubleValue() < 0d)
                    return false;
            if (((String) tipoCombobox.getSelectedItem()).equals("Gerencial"))
                if (bonificacaoPessoaText.getValue() == null || ((Number) bonificacaoPessoaText.getValue()).doubleValue() < 0d)
                    return false;
        }
        catch (ClassCastException e) {
            return false;
        }
        return true;
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

        GridLayout labelsLayout = new GridLayout(9, 1);
        GridLayout fieldsLayout = new GridLayout(9, 1);
        labelsLayout.setVgap(10);
        fieldsLayout.setVgap(10);

        panelLabels.setLayout(labelsLayout);
        panelFields.setLayout(fieldsLayout);

        panelLabels.add(new JLabel("   Tipo:"));
        panelLabels.add(new JLabel("   Nome:"));
        panelLabels.add(new JLabel("   Salário nível 1:"));
        panelLabels.add(new JLabel("   Salário nível 2:"));
        panelLabels.add(new JLabel("   Salário nível 3:"));
        panelLabels.add(new JLabel("   Salários bonificação:"));

        JLabel acessoLabel = new JLabel("   Acessa sistema:");
        panelLabels.add(acessoLabel);

        JLabel bdepartamentoLabel = new JLabel("   Bonificação por departamento:");
        panelLabels.add(bdepartamentoLabel);

        JLabel bpessoaLabel = new JLabel("   Bonificação por pessoa:");
        panelLabels.add(bpessoaLabel);

        NumberFormat format = NumberFormat.getCurrencyInstance();


        tipoCombobox = new JComboBox<>(new String[]{ "Executivo", "Gerencial", "Operacional" });

        nomeText = new JTextField();
        salarioNivel1Text = new JFormattedTextField(format);
        salarioNivel2Text = new JFormattedTextField(format);
        salarioNivel3Text = new JFormattedTextField(format);
        porcentagemBonificacaoText = new JFormattedTextField(new DecimalFormat("#.#"));
        bonificacaoDepartamentoText = new JFormattedTextField(format);
        bonificacaoPessoaText = new JFormattedTextField(format);

        salarioNivel1Text.setValue(BigDecimal.ZERO);
        salarioNivel2Text.setValue(BigDecimal.ZERO);
        salarioNivel3Text.setValue(BigDecimal.ZERO);
        porcentagemBonificacaoText.setValue(BigDecimal.ZERO);
        acessoCheckbox = new JCheckBox();
        bonificacaoDepartamentoText.setValue(BigDecimal.ZERO);
        bonificacaoPessoaText.setValue(BigDecimal.ZERO);

        panelFields.add(tipoCombobox);
        panelFields.add(nomeText);
        panelFields.add(salarioNivel1Text);
        panelFields.add(salarioNivel2Text);
        panelFields.add(salarioNivel3Text);
        panelFields.add(porcentagemBonificacaoText);
        panelFields.add(acessoCheckbox);
        panelFields.add(bonificacaoDepartamentoText);
        panelFields.add(bonificacaoPessoaText);

        String tipo = (String) tipoCombobox.getSelectedItem();

        bdepartamentoLabel.setVisible(tipo.equals("Executivo"));
        bonificacaoDepartamentoText.setVisible(tipo.equals("Executivo"));

        bpessoaLabel.setVisible(tipo.equals("Gerencial"));
        bonificacaoPessoaText.setVisible(tipo.equals("Gerencial"));

        acessoLabel.setVisible(tipo.equals("Operacional"));
        acessoCheckbox.setVisible(tipo.equals("Operacional"));

        tipoCombobox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                String tipo = (String) tipoCombobox.getSelectedItem();

                bdepartamentoLabel.setVisible(tipo.equals("Executivo"));
                bonificacaoDepartamentoText.setVisible(tipo.equals("Executivo"));

                bpessoaLabel.setVisible(tipo.equals("Gerencial"));
                bonificacaoPessoaText.setVisible(tipo.equals("Gerencial"));

                acessoLabel.setVisible(tipo.equals("Operacional"));
                acessoCheckbox.setVisible(tipo.equals("Operacional"));
            }
        });

        return panel;
    }

    @Override
    protected void fillFields(CargoBean bean) {
        nomeText.setText(bean.getNome());
        salarioNivel1Text.setValue(bean.getSalarioNivel1());
        salarioNivel2Text.setValue(bean.getSalarioNivel2());
        salarioNivel3Text.setValue(bean.getSalarioNivel3());
        porcentagemBonificacaoText.setValue(bean.getPercentualSalarioBonus());

        if (bean instanceof CargoGerencialBean) {
            tipoCombobox.setSelectedItem("Gerencial");
            if (((CargoGerencialBean) bean).getBonificacaoPorPessoa() != null)
              bonificacaoPessoaText.setValue(((CargoGerencialBean) bean).getBonificacaoPorPessoa());
        }
        else if (bean instanceof CargoOperacionalBean) {
            tipoCombobox.setSelectedItem("Operacional");
            acessoCheckbox.setSelected(((CargoOperacionalBean) bean).getAcessaSistema());
        }
        else if (bean instanceof CargoExecutivoBean) {
            tipoCombobox.setSelectedItem("Executivo");
            if (((CargoExecutivoBean) bean).getBonificacaoPorDepartamento() != null)
              bonificacaoDepartamentoText.setValue(((CargoExecutivoBean) bean).getBonificacaoPorDepartamento());
        }
    }
}
