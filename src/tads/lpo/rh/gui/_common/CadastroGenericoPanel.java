package tads.lpo.rh.gui._common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class CadastroGenericoPanel<T, S extends CadastroGenericoEvents<T>> extends JPanel {

    private JTable table;

    private EditAndDeleteTableModel<T> tableModel;

    private S events;

    private ColumnDeclaration<T, ? extends Comparable> sortType;

    private JTextField filtroText;

    protected abstract EditAndDeleteTableModel<T> getEditAndDeleteTableModel();

    public CadastroGenericoPanel(S events) {
        this.events = events;

        ButtonColumn btnColumnEditar = new ButtonColumn("Editar");
        ButtonColumn btnColumnExcluir = new ButtonColumn("Excluir");

        btnColumnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    events.modalEditar((T) event.getSource());
                }
                catch (ClassCastException e) {
                    ErroFrame.exibirErro(e);
                }
            }
        });

        btnColumnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    events.excluir((T) event.getSource());
                }
                catch (ClassCastException e) {
                    ErroFrame.exibirErro(e);
                }
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
                events.modalNovo();
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

        List<T> itens = events.listar(filtroText.getText());

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

    protected JTable getTable() {
        return this.table;
    }
}
