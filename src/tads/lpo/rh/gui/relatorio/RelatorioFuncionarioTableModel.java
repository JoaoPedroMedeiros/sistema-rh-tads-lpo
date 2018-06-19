package tads.lpo.rh.gui.relatorio;

import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelList;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class RelatorioFuncionarioTableModel extends TableModelList<FuncionarioBean> {

    private final List<ColumnDeclaration<FuncionarioBean, ?>> columns;

    public RelatorioFuncionarioTableModel() {
        columns =
            Arrays.asList(
                    new ColumnDeclaration<FuncionarioBean, String>("Cargo",            (f) -> f.getCargo() != null ? f.getCargo().getNome() : "Sem cargo"),
                    new ColumnDeclaration<FuncionarioBean, String>("Nome completo",    (f) -> f.getNome() + " " + f.getSobrenome()),
                    new ColumnDeclaration<FuncionarioBean, Integer>("Nível",           (f) -> f.getNivel()),
                    new ColumnDeclaration<FuncionarioBean, String>("Salário",          (f) -> NumberFormat.getCurrencyInstance().format(f.getSalario())),
                    new ColumnDeclaration<FuncionarioBean, String>("Bônus anual",      (f) -> NumberFormat.getCurrencyInstance().format(f.getBonificacaoAnual()))
            );
    }

    @Override
    protected List<ColumnDeclaration<FuncionarioBean, ?>> getColumns() {
        return columns;
    }
}
