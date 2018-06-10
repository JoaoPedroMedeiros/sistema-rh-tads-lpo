package tads.lpo.rh.gui._common.tablemodel;

import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class TableModelList<T> extends AbstractTableModel {

    private final List<T> items;

    public TableModelList() {
        this.items = new LinkedList<>();
    }

    @Override
    public int getRowCount() {
        return this.items.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getColumns().get(columnIndex).getValueAtStrategy().call(getItems().get(rowIndex));
    }

    @Override
    public int getColumnCount() {
        return getColumns().size();
    }

    @Override
    public String getColumnName(int col) {
        return getColumns().get(col).getName();
    }

    public void setItems(List<T> items) {
        this.items.clear();

        if (items != null)
          this.items.addAll(items);
    }

    public List<T> getItems() {
        return new ArrayList<>(this.items);
    }

    protected abstract List<ColumnDeclaration<T, ?>> getColumns();
}
