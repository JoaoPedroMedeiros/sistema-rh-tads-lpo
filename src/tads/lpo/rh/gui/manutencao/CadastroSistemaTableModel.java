package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.gui._common.ColumnDeclaration;
import tads.lpo.rh.gui._common.EditAndDeleteTableModel;

import java.util.Arrays;
import java.util.List;

public class CadastroSistemaTableModel extends EditAndDeleteTableModel<SistemaBean> {

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
