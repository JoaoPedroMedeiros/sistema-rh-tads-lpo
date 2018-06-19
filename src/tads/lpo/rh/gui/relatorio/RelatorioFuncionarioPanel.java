package tads.lpo.rh.gui.relatorio;

import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.dao.FuncionarioDAO;
import tads.lpo.rh.gui._common.ErroFrame;

import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class RelatorioFuncionarioPanel extends JPanel {

    public RelatorioFuncionarioPanel() {
        try {
            setLayout(new GridLayout(1, 1));

            RelatorioFuncionarioTableModel tableModel = new RelatorioFuncionarioTableModel();

            JTable table = new javax.swing.JTable(tableModel);
            table.setModel(tableModel);

            JScrollPane scrollPanel = new javax.swing.JScrollPane();
            scrollPanel.setViewportView(table);

            add(scrollPanel);

            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            List<FuncionarioBean> funcionarios = funcionarioDAO.consultarFuncionariosRelatorio();

            tableModel.setItems(funcionarios);
            tableModel.fireTableDataChanged();
        }
        catch (SQLException e) {
            ErroFrame.exibirErro(e);
        }
    }
}
