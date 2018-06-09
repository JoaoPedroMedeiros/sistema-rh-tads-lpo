package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.ColumnDeclaration;
import tads.lpo.rh.gui._common.EditAndDeleteTableModel;

import java.util.Arrays;
import java.util.List;

public class CadastroSistemaPanel extends CadastroGenericoPanel<SistemaBean, CadastroSistemaEvents> {

    public CadastroSistemaPanel(CadastroSistemaEvents events) {
        super(events);

        getTable().getColumnModel().getColumn(0).setMaxWidth(80);

    }

    @Override
    protected EditAndDeleteTableModel<SistemaBean> getEditAndDeleteTableModel() {
        return new CadastroSistemaTableModel();
    }

    @Override
    protected List<ColumnDeclaration<SistemaBean, ? extends Comparable>> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<SistemaBean, String>("Nome", (s) -> s.getNome()),
                new ColumnDeclaration<SistemaBean, Integer>("ID", (s) -> s.getId())
        );
    }
}
