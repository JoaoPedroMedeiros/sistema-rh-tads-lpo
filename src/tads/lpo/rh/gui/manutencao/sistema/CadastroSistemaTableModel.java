package tads.lpo.rh.gui.manutencao.sistema;

import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.util.Arrays;
import java.util.List;

public class CadastroSistemaTableModel extends TableModelEditAndDelete<SistemaBean> {

    private final List<ColumnDeclaration<SistemaBean, ?>> columns;

    public CadastroSistemaTableModel() {
        columns =
            Arrays.asList(
                new ColumnDeclaration<SistemaBean, Integer>("ID", (d) -> d.getId()),
                new ColumnDeclaration<SistemaBean, String>("Nome", (d) -> d.getNome())
            );
    }

    @Override
    protected List<ColumnDeclaration<SistemaBean, ?>> getColumns() {
        return columns;
    }
}
