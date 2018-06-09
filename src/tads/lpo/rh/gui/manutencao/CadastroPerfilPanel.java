package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.PerfilBean;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.ColumnDeclaration;
import tads.lpo.rh.gui._common.EditAndDeleteTableModel;

import java.util.Arrays;
import java.util.List;

public class CadastroPerfilPanel extends CadastroGenericoPanel<PerfilBean, CadastroPerfilEvents> {

    public CadastroPerfilPanel(CadastroPerfilEvents events) {
        super(events);

        getTable().getColumnModel().getColumn(0).setMaxWidth(80);

    }

    @Override
    protected EditAndDeleteTableModel<PerfilBean> getEditAndDeleteTableModel() {
        return new CadastroPerfilTableModel();
    }

    @Override
    protected List<ColumnDeclaration<PerfilBean, ? extends Comparable>> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<PerfilBean, String>("Nome", (d) -> d.getFuncionario().getNome()),
                new ColumnDeclaration<PerfilBean, Integer>("ID", (d) -> d.getId())
        );
    }
}
