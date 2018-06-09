package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.CargoBean;
import tads.lpo.rh.bean.CargoExecutivoBean;
import tads.lpo.rh.bean.CargoGerencialBean;
import tads.lpo.rh.bean.CargoOperacionalBean;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.ColumnDeclaration;
import tads.lpo.rh.gui._common.EditAndDeleteTableModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CadastroCargoPanel extends CadastroGenericoPanel {

    public CadastroCargoPanel(CadastroCargoEvents events) {
        super(events);
    }

    @Override
    protected EditAndDeleteTableModel getEditAndDeleteTableModel() {
        return new CadastroCargoTableModel();
    }

    @Override
    protected List<ColumnDeclaration> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<CargoBean, Integer>("ID", (c) -> c.getId()),
                new ColumnDeclaration<CargoBean, String>("Nome", (c) -> c.getNome()),
                new ColumnDeclaration<CargoBean, String>("Tipo", (c) -> {
                    if (c instanceof CargoExecutivoBean)
                        return "Executivo";
                    if (c instanceof CargoGerencialBean)
                        return "Gerencial";
                    if (c instanceof CargoOperacionalBean)
                        return "Operacional";
                    return "Não identificado";
                }),
                new ColumnDeclaration<CargoBean, BigDecimal>("Salário n. 1", (c) -> c.getSalarioNivel1()),
                new ColumnDeclaration<CargoBean, BigDecimal>("Salário n. 2", (c) -> c.getSalarioNivel2()),
                new ColumnDeclaration<CargoBean, BigDecimal>("Salário n. 3", (c) -> c.getSalarioNivel3()),
                new ColumnDeclaration<CargoBean, BigDecimal>("Percentual", (c) -> c.getPercentualSalarioBonus())
        );
    }
}
