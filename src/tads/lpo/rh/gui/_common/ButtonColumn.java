package tads.lpo.rh.gui._common;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private ActionListener actionListener;

    private String text;

    public ButtonColumn(String text) {
        super();
        this.text = text;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton btn = new JButton(this.text);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null) {
                    e.setSource(value);
                    actionListener.actionPerformed(e);
                }
            }
        });
        return btn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JButton btn = new JButton(this.text);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null) {
                    e.setSource(value);
                    actionListener.actionPerformed(e);
                }
            }
        });
        return btn;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    public void addActionListener(ActionListener listener) {
        this.actionListener = listener;
    }
}