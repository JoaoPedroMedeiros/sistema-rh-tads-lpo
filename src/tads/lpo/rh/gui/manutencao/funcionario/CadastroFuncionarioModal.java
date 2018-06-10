package tads.lpo.rh.gui.manutencao.funcionario;

import tads.lpo.rh.bean.*;
import tads.lpo.rh.dao.CargoDAO;
import tads.lpo.rh.dao.DepartamentoDAO;
import tads.lpo.rh.gui._common.CadastroGenericoModal;
import tads.lpo.rh.gui._common.ErroFrame;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CadastroFuncionarioModal extends CadastroGenericoModal<FuncionarioBean> {

    public static final Dimension dimensions = new Dimension(600, 500);

    private JTabbedPane tabPane;
    private JTextField nameText;
    private JTextField sobrenomeText;
    private JTextField rgText;
    private JFormattedTextField cpfText;
    private JFormattedTextField telefoneText;
    private JPasswordField senhaText;
    private JComboBox<CargoBean> cargoCombobox;
    private JComboBox<Integer> nivelCombobox;
    private JComboBox<DepartamentoBean> departamentoCombobox;
    private JComboBox<DepartamentoBean> departamentoGerenciadoCombobox;
    private JList<DepartamentoBean> departamentosList;
    //private JCheckBox acessoCheckbox;

    private List<DepartamentoBean> departamentos;

    public CadastroFuncionarioModal(FuncionarioBean funcionario) {
        super(funcionario);
    }

    public CadastroFuncionarioModal() {
        super();
    }

    @Override
    protected Container initializeFields() {
        try {
            departamentos = new DepartamentoDAO().listarTodos(null);

            tabPane = new JTabbedPane();
            tabPane.add("Dados do funcionário", createTabDadosFuncionario());
            tabPane.add("Departamentos", createTabDepartamentos(departamentos));

            nivelCombobox.addItem(1);
            nivelCombobox.addItem(2);
            nivelCombobox.addItem(3);

            departamentos.forEach((d) -> {
                departamentoGerenciadoCombobox.addItem(d);
                departamentoCombobox.addItem(d);
            });

            new CargoDAO().listarTodos(null).forEach(c -> cargoCombobox.addItem(c));

        } catch (SQLException e) {
            ErroFrame.exibirErro(e);
        }

        return tabPane;
    }

    private JPanel createTabDadosFuncionario() {
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

        GridLayout labelsLayout = new GridLayout(10, 1);
        GridLayout fieldsLayout = new GridLayout(10, 1);
        labelsLayout.setVgap(10);
        fieldsLayout.setVgap(10);

        panelLabels.setLayout(labelsLayout);
        panelFields.setLayout(fieldsLayout);

        panelLabels.add(new JLabel("   Cargo:"));
        panelLabels.add(new JLabel("   Nome:"));
        panelLabels.add(new JLabel("   Sobrenome:"));
        panelLabels.add(new JLabel("   RG:"));
        panelLabels.add(new JLabel("   CPF:"));
        panelLabels.add(new JLabel("   Telefone:"));
        panelLabels.add(new JLabel("   Nível:"));
        panelLabels.add(new JLabel("   Senha:"));
        JLabel depLabel = new JLabel("   Departamento:");
        panelLabels.add(depLabel);
        //JLabel acessoLabel = new JLabel("   Acessa sistema:");
        //panelLabels.add(acessoLabel);
        JLabel depGerLabel = new JLabel("   Dep. gerenciado:");
        panelLabels.add(depGerLabel);

        nameText = new JTextField();
        sobrenomeText = new JTextField();
        rgText = new JTextField();
        cpfText = new JFormattedTextField();
        telefoneText = new JFormattedTextField();
        senhaText = new JPasswordField();
        cargoCombobox = new JComboBox<>();
        nivelCombobox = new JComboBox<>();
        departamentoCombobox = new JComboBox<>();
        departamentoGerenciadoCombobox = new JComboBox<>();

        try {
            cpfText.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            telefoneText.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("(##)#####-####")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        panelFields.add(cargoCombobox);
        panelFields.add(nameText);
        panelFields.add(sobrenomeText);
        panelFields.add(rgText);
        panelFields.add(cpfText);
        panelFields.add(telefoneText);
        panelFields.add(nivelCombobox);
        panelFields.add(senhaText);
        panelFields.add(departamentoCombobox);
        //panelFields.add(acessoCheckbox);
        panelFields.add(departamentoGerenciadoCombobox);

        //acessoCheckbox.setSelected(true);

        cargoCombobox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                CargoBean cargo = (CargoBean) cargoCombobox.getSelectedItem();

                if (cargo instanceof CargoExecutivoBean) {
                    tabPane.setEnabledAt(1, true);
                }
                else {
                    tabPane.setEnabledAt(1, false);
                }

                if (cargo instanceof CargoGerencialBean) {
                    departamentoGerenciadoCombobox.setVisible(true);
                    depGerLabel.setVisible(true);
                }
                else {
                    departamentoGerenciadoCombobox.setVisible(false);
                    depGerLabel.setVisible(false);
                }

                if (cargo instanceof CargoOperacionalBean) {
                    departamentoCombobox.setVisible(true);
                    depLabel.setVisible(true);
                }
                else {
                    departamentoCombobox.setVisible(false);
                    depLabel.setVisible(false);
                }
            }
        });

        return panel;
    }

    private JPanel createTabDepartamentos(List<DepartamentoBean> departamentos) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));

        departamentosList = new JList<>(departamentos.toArray(new  DepartamentoBean[departamentos.size()]));
        departamentosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        panel.add(departamentosList);

        return panel;
    }

    @Override
    protected boolean validateFields() {
        if (cargoCombobox.getSelectedItem() == null)
            return false;
        if (departamentoCombobox.getSelectedItem() == null)
            return false;
        if (nameText.getText().isEmpty())
            return false;
        if (sobrenomeText.getText().isEmpty())
            return false;
        if (rgText.getText().isEmpty())
            return false;
        if (cpfText.getText().isEmpty())
            return false;
        if (telefoneText.getText().isEmpty())
            return false;
        if (nivelCombobox.getSelectedItem() == null)
            return false;
        if (senhaText.getText().isEmpty())
            return false;

        CargoBean cargo = (CargoBean) cargoCombobox.getSelectedItem();

        if (cargo instanceof CargoExecutivoBean)
            if (departamentosList.getSelectedIndices().length == 0)
                return false;

        if (cargo instanceof CargoGerencialBean)
            if (departamentoGerenciadoCombobox.getSelectedItem() == null)
                return false;

        return true;
    }

    @Override
    protected FuncionarioBean generateBean() {
        CargoBean cargo = (CargoBean) cargoCombobox.getSelectedItem();

        FuncionarioBean funcionario;

        if (cargo instanceof CargoExecutivoBean) {
            funcionario = new FuncionarioExecutivoBean((CargoExecutivoBean) cargo);
            ((FuncionarioExecutivoBean) funcionario).setDepartamentos(departamentosList.getSelectedValuesList());
        }
        else if (cargo instanceof CargoGerencialBean) {
            funcionario = new FuncionarioGerencialBean((CargoGerencialBean) cargo);
            ((FuncionarioGerencialBean) funcionario).setDepartamentoGerenciado((DepartamentoBean) departamentoGerenciadoCombobox.getSelectedItem());
        }
        else if (cargo instanceof CargoOperacionalBean) {
            funcionario = new FuncionarioOperacionalBean((CargoOperacionalBean) cargo);
            ((FuncionarioOperacionalBean) funcionario).setDepartamentoAtuacao((DepartamentoBean) departamentoCombobox.getSelectedItem());
            //((FuncionarioOperacionalBean) funcionario).setAcessaSistema(acessoCheckbox.isSelected());
        }
        else
            throw new IllegalArgumentException();

        funcionario.setSenha(senhaText.getText());
        funcionario.setTelefone(telefoneText.getText());
        funcionario.setNivel((Integer) nivelCombobox.getSelectedItem());
        funcionario.setCpf(cpfText.getText());
        funcionario.setRg(rgText.getText());
        funcionario.setSobrenome(sobrenomeText.getText());
        funcionario.setNome(nameText.getText());

        return funcionario;
    }

    @Override
    protected void fillFields(FuncionarioBean funcionario) {
        this.nameText.setText(funcionario.getNome());
        this.sobrenomeText.setText(funcionario.getSobrenome());
        this.rgText.setText(funcionario.getRg());
        this.cpfText.setText(funcionario.getCpf());
        this.telefoneText.setText(funcionario.getTelefone());
        this.cargoCombobox.setSelectedItem(funcionario.getCargo());
        this.nivelCombobox.setSelectedItem(funcionario.getNivel());

        this.senhaText.setText(funcionario.getSenha());

        if (funcionario instanceof FuncionarioGerencialBean)
            if (((FuncionarioGerencialBean) funcionario).getDepartamentoGerenciado() != null)
                this.departamentoGerenciadoCombobox.setSelectedItem(((FuncionarioGerencialBean) funcionario).getDepartamentoGerenciado());

        if (funcionario instanceof FuncionarioExecutivoBean) {
            List<Integer> indices = new ArrayList<>();

            if (((FuncionarioExecutivoBean) funcionario).getDepartamentos() != null) {
                departamentos.forEach(d -> {
                    ((FuncionarioExecutivoBean) funcionario).getDepartamentos().forEach(d1 -> {
                        if (d.getId().equals(d1.getId()))
                            indices.add(departamentos.indexOf(d));
                    });
                });

                int[] indicesArr = new int[indices.size()];
                for (int i = 0; i < indices.size(); i++)
                    indicesArr[i] = indices.get(i);

                departamentosList.setSelectedIndices(indicesArr);
            }
        }

        if (funcionario instanceof FuncionarioOperacionalBean) {
            if (((FuncionarioOperacionalBean) funcionario).getDepartamentoAtuacao() != null)
                this.departamentoCombobox.setSelectedItem(((FuncionarioOperacionalBean) funcionario).getDepartamentoAtuacao());
            //if (((FuncionarioOperacionalBean) funcionario).getAcessaSistema() != null)
            //    this.acessoCheckbox.setSelected(((FuncionarioOperacionalBean) funcionario).getAcessaSistema());
        }
    }
}
