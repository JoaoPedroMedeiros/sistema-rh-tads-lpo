package tads.lpo.rh.gui.manutencao.departamento;

import tads.lpo.rh.bean.DepartamentoBean;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.util.Arrays;
import java.util.List;

public class CadastroDepartamentoTableModel extends TableModelEditAndDelete<DepartamentoBean> {

    private final List<ColumnDeclaration<DepartamentoBean, ?>> columns;

    public CadastroDepartamentoTableModel() {
        columns =
            Arrays.asList(
                new ColumnDeclaration<DepartamentoBean, Integer>("ID", (d) -> d.getId()),
                new ColumnDeclaration<DepartamentoBean, String>("Nome", (d) -> d.getNome())
            );
    }

    @Override
    protected List<ColumnDeclaration<DepartamentoBean, ?>> getColumns() {
        return columns;
    }
}
