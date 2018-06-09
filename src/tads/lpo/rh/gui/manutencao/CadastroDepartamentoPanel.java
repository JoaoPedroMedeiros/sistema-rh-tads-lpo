package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.DepartamentoBean;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.ColumnDeclaration;
import tads.lpo.rh.gui._common.EditAndDeleteTableModel;

import java.util.Arrays;
import java.util.List;

public class CadastroDepartamentoPanel extends CadastroGenericoPanel<DepartamentoBean, CadastroDepartamentoEvents> {

    public CadastroDepartamentoPanel(CadastroDepartamentoEvents events) {
        super(events);

        getTable().getColumnModel().getColumn(0).setMaxWidth(80);

    }

    @Override
    protected EditAndDeleteTableModel<DepartamentoBean> getEditAndDeleteTableModel() {
        return new CadastroDepartamentoTableModel();
    }

    @Override
    protected List<ColumnDeclaration<DepartamentoBean, ? extends Comparable>> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<DepartamentoBean, String>("Nome", (d) -> d.getNome()),
                new ColumnDeclaration<DepartamentoBean, Integer>("ID", (d) -> d.getId())
        );
    }
}
