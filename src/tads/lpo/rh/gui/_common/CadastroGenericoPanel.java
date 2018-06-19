package tads.lpo.rh.gui._common;

import tads.lpo.rh.bean.BaseBean;
import tads.lpo.rh.dao.CrudDAO;
import tads.lpo.rh.gui._common.tablemodel.ButtonColumn;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class CadastroGenericoPanel<T extends BaseBean> extends JPanel {

    private final Frame parentFrame;

    private JDialog dialog;

    private JTable table;

    private TableModelEditAndDelete<T> tableModel;

    private ColumnDeclaration<T, ? extends Comparable> sortType;

    private JTextField filtroText;

    protected abstract TableModelEditAndDelete<T> getEditAndDeleteTableModel();

    public CadastroGenericoPanel(Frame parentFrame) {
        this.parentFrame = parentFrame;

        ButtonColumn btnColumnEditar = new ButtonColumn("Editar");
        ButtonColumn btnColumnExcluir = new ButtonColumn("Excluir");

        btnColumnEditar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    CadastroGenericoModal<T> modal;
                    T target = (T) event.getSource();
                    carregarInformacoesAdicionaisEdicao(target);
                    modal = criarModalEditar(target);

                    modal.setSavedListener((savedEvent) -> {

                        try {
                            getDAO().alterar((T) savedEvent.getSource());

                            dialog.dispose();
                            refresh();

                        } catch (SQLException e1) {
                            ErroFrame.exibirErro(e1);
                        }
                    });

                    openModal(modal);
                }
                catch (ClassCastException e) {
                    ErroFrame.exibirErro(e);
                }
            }
        });

        btnColumnExcluir.addActionListener((event) -> {

            try {
                getDAO().excluir((T) event.getSource());
                refresh();
            }
            catch (SQLException | ClassCastException e) {
                ErroFrame.exibirErro(e);
            }
        });

        tableModel = getEditAndDeleteTableModel();

        table = new javax.swing.JTable();
        table.setModel(tableModel);
        table.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setPreferredWidth(100);
        table.getColumnModel().getColumn(tableModel.getColumnCount() - 2).setPreferredWidth(100);
        table.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setMaxWidth(100);
        table.getColumnModel().getColumn(tableModel.getColumnCount() - 2).setMaxWidth(100);

        table.getColumnModel().getColumn(tableModel.getColumnCount() - 2).setCellEditor(btnColumnEditar);
        table.getColumnModel().getColumn(tableModel.getColumnCount() - 2).setCellRenderer(btnColumnEditar);
        table.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellEditor(btnColumnExcluir);
        table.getColumnModel().getColumn(tableModel.getColumnCount() - 1).setCellRenderer(btnColumnExcluir);

        filtroText = new JTextField();
        filtroText.setPreferredSize(new Dimension(180,25));

        JButton listarButton = new JButton("Filtrar");
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                refresh();
            }
        });

        JButton novoButton = new JButton("Novo");
        novoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroGenericoModal<T> modal;
                modal = criarModalNovo();
                modal.setSavedListener((savedEvent) -> {

                    try {
                        getDAO().cadastrar((T) savedEvent.getSource());

                        dialog.dispose();
                        refresh();

                    } catch (SQLException e1) {
                        ErroFrame.exibirErro(e1);
                    }
                });

                openModal(modal);
            }
        });

        JComboBox<ColumnDeclaration<T, ? extends Comparable>> ordenacaoCombobox = new JComboBox(getSortColumns().toArray());
        sortType = (ColumnDeclaration<T, ? extends Comparable>) ordenacaoCombobox.getSelectedItem();
        ordenacaoCombobox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sortType = (ColumnDeclaration<T, ? extends Comparable>) ordenacaoCombobox.getSelectedItem();
                refresh();
            }
        });

        JPanel panelFiltro = new JPanel();
        panelFiltro.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(filtroText);
        panelFiltro.add(listarButton);

        JPanel panelNovoOrd = new JPanel();
        panelNovoOrd.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelNovoOrd.add(novoButton);
        panelNovoOrd.add(ordenacaoCombobox);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.LEADING));
        panelBotoes.add(panelFiltro);
        panelBotoes.add(panelNovoOrd);

        JScrollPane scrollPanel = new javax.swing.JScrollPane();
        scrollPanel.setViewportView(table);

        setLayout(new BorderLayout());
        add(panelBotoes, "North");
        add(scrollPanel, "Center");
    }

    public void refresh() {

        List<T> itens;
        try {
            itens = getDAO().listarTodos(filtroText.getText());

        } catch (SQLException e) {
            ErroFrame.exibirErro(e);
            return;
        }

        Collections.sort(itens, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return sortType.getValueAtStrategy().call(o1).compareTo(sortType.getValueAtStrategy().call(o2));
            }
        });

        tableModel.setItems(itens);
        tableModel.fireTableDataChanged();
    }

    protected abstract List<ColumnDeclaration<T, ? extends Comparable>> getSortColumns();

    protected abstract CadastroGenericoModal<T> criarModalNovo();

    protected abstract CadastroGenericoModal<T> criarModalEditar(T target);

    protected abstract CrudDAO<T> getDAO();

    protected abstract Dimension modalDimensions();

    protected JTable getTable() {
        return this.table;
    }

    protected void carregarInformacoesAdicionaisEdicao(T target) {
    }

    private void openModal(JPanel panel) {
        dialog = new JDialog(this.parentFrame, "Informações", true);
        dialog.getContentPane().add(panel);

        dialog.setSize(modalDimensions());
        dialog.setPreferredSize(modalDimensions());

        FrameUtils.centralizar(dialog);

        dialog.setResizable(false);
        dialog.setVisible(true);
        dialog.pack();
    }
}
