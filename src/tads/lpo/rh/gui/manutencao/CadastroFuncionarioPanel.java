package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.ColumnDeclaration;
import tads.lpo.rh.gui._common.EditAndDeleteTableModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CadastroFuncionarioPanel extends CadastroGenericoPanel {

    public CadastroFuncionarioPanel(CadastroFuncionarioEvents events) {
        super(events);
    }

    @Override
    protected EditAndDeleteTableModel getEditAndDeleteTableModel() {
        return new CadastroFuncionarioTableModel();
    }

    @Override
    protected List<ColumnDeclaration> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<FuncionarioBean, String>("Nome completo", (f) -> f.getNome()),
                new ColumnDeclaration<FuncionarioBean, BigDecimal>("Salário", (f) -> BigDecimal.ZERO)
        );
    }
}
