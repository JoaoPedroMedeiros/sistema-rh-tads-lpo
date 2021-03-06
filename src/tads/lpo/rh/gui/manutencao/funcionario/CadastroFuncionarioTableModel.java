package tads.lpo.rh.gui.manutencao.funcionario;

import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class CadastroFuncionarioTableModel extends TableModelEditAndDelete<FuncionarioBean> {

    private final List<ColumnDeclaration<FuncionarioBean, ?>> columns;

    public CadastroFuncionarioTableModel() {
        columns =
            Arrays.asList(
                new ColumnDeclaration<FuncionarioBean, String>("Cargo",            (f) -> f.getCargo() != null ? f.getCargo().getNome() : "Sem cargo"),
                new ColumnDeclaration<FuncionarioBean, String>("Nome completo",    (f) -> f.getNome() + " " + f.getSobrenome()),
                new ColumnDeclaration<FuncionarioBean, String>("CPF",              (f) -> f.getCpf()),
                new ColumnDeclaration<FuncionarioBean, String>("RG",               (f) -> f.getRg()),
                new ColumnDeclaration<FuncionarioBean, String>("Telefone",         (f) -> f.getTelefone()),
                new ColumnDeclaration<FuncionarioBean, Integer>("Nível",           (f) -> f.getNivel()),
                new ColumnDeclaration<FuncionarioBean, String>("Salário",          (f) -> NumberFormat.getCurrencyInstance().format(f.getSalario()))
            );
    }

    @Override
    protected List<ColumnDeclaration<FuncionarioBean, ?>> getColumns() {
        return columns;
    }
}
