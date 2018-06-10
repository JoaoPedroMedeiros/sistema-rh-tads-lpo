package tads.lpo.rh.gui.manutencao.perfil;

import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.bean.PerfilBean;
import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.dao.FuncionarioDAO;
import tads.lpo.rh.dao.PerfilDAO;
import tads.lpo.rh.dao.SistemaDAO;
import tads.lpo.rh.gui._common.CadastroGenericoModal;
import tads.lpo.rh.gui._common.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CadastroPerfilModal extends CadastroGenericoModal<PerfilBean> {

    public static final Dimension dimensions = new Dimension(700, 400);

    private JComboBox<FuncionarioBean> funcionarioCombobox;

    private JList<SistemaBean> sistemasList;

    private List<SistemaBean> sistemas;

    public CadastroPerfilModal(PerfilBean bean) {
        super(bean);
    }

    public CadastroPerfilModal() {
        super();
    }

    @Override
    protected Component initializeFields() throws SQLException {

        JPanel panel = new JPanel();

        BorderLayout layout = new BorderLayout();
        layout.setVgap(20);
        layout.setHgap(10);

        panel.setLayout(layout);

        JPanel espacamentoNorte = new JPanel();
        JPanel espacamentoLeste = new JPanel();
        JPanel espacamentoSul = new JPanel();
        JPanel espacamentoOeste = new JPanel();

        panel.add(espacamentoNorte, "North");
        panel.add(espacamentoLeste, "East");
        panel.add(espacamentoSul, "South");
        panel.add(espacamentoOeste, "West");

        JPanel panelFields = new JPanel();
        panelFields.setLayout(new BorderLayout());

        panel.add(panelFields, "Center");

        JPanel panelFuncionario = new JPanel();
        panelFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panelSistemas = new JPanel();
        panelSistemas.setLayout(new GridLayout(1, 1));


        panelFields.add(panelFuncionario, "North");
        panelFields.add(panelSistemas, "Center");

        List<FuncionarioBean> funcionarios = new FuncionarioDAO().listarTodos(null);
        sistemas = new SistemaDAO().listarTodos(null);

        funcionarioCombobox = new JComboBox<FuncionarioBean>(funcionarios.toArray(new FuncionarioBean[funcionarios.size()]));
        funcionarioCombobox.setMaximumSize(new Dimension(300, 40));

        panelFuncionario.add(new JLabel("Funcionário"));
        panelFuncionario.add(funcionarioCombobox);

        sistemasList = new JList<>((SistemaBean[]) sistemas.toArray(new SistemaBean[sistemas.size()]));
        sistemasList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        panelSistemas.add(sistemasList);

        return panel;
    }

    @Override
    protected PerfilBean generateBean() {
        PerfilBean perfilBean = new PerfilBean((FuncionarioBean) funcionarioCombobox.getSelectedItem(),
                sistemasList.getSelectedValuesList());
        return perfilBean;
    }

    @Override
    protected boolean validateFields() throws SQLException {

        if (funcionarioCombobox.getSelectedItem() == null)
            return false;

        if (sistemasList.getSelectedIndices() == null || sistemasList.getSelectedIndices().length == 0)
            return false;

        if (getId() == null)
            for (PerfilBean perfilBean : new PerfilDAO().listarTodos(null)) {
                if (perfilBean.getFuncionario().equals(funcionarioCombobox.getSelectedItem()))
                    throw new ValidationException("O funcionário " + perfilBean.getFuncionario().getNome() + " já tem um perfil cadastrado");
            }

        return true;
    }

    @Override
    protected void fillFields(PerfilBean bean) {
        List<Integer> indices = new ArrayList<>();

        funcionarioCombobox.setEnabled(false);

        if (bean.getSistemas() != null) {
            sistemas.forEach(d -> {
                bean.getSistemas().forEach(d1 -> {
                    if (d.equals(d1))
                        indices.add(sistemas.indexOf(d));
                });
            });

            int[] indicesArr = new int[indices.size()];
            for (int i = 0; i < indices.size(); i++)
                indicesArr[i] = indices.get(i);

            sistemasList.setSelectedIndices(indicesArr);
        }

        if (bean.getFuncionario() != null)
            funcionarioCombobox.setSelectedItem(bean.getFuncionario());
    }
}
