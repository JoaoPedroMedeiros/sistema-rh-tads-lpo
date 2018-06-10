package tads.lpo.rh.gui._common.tablemodel;

import javax.swing.*;

public abstract class TableModelEditAndDelete<T> extends TableModelList<T> {

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex < getColumns().size()) {
            return super.getValueAt(rowIndex, columnIndex);
        }
        if (columnIndex == getColumns().size()) {
            return getItems().get(rowIndex);
        }
        else {
            return getItems().get(rowIndex);
        }
    }

    @Override
    public int getColumnCount() {
        return getColumns().size() + 2;
    }

    @Override
    public String getColumnName(int col) {
        if (col < getColumns().size()) {
            return super.getColumnName(col);
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex >= getColumns().size()) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex >= getColumns().size()) {
            return true;
        }
        return super.isCellEditable(rowIndex, columnIndex);
    }
}
