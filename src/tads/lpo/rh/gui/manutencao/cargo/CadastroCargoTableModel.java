package tads.lpo.rh.gui.manutencao.cargo;

import tads.lpo.rh.bean.CargoBean;
import tads.lpo.rh.bean.CargoExecutivoBean;
import tads.lpo.rh.bean.CargoGerencialBean;
import tads.lpo.rh.bean.CargoOperacionalBean;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class CadastroCargoTableModel extends TableModelEditAndDelete<CargoBean> {

    private final List<ColumnDeclaration<CargoBean, ?>> columns;

    public CadastroCargoTableModel() {
        columns =
            Arrays.asList(
                    new ColumnDeclaration<CargoBean, Integer>("ID", (c) -> c.getId()),
                    new ColumnDeclaration<CargoBean, String>("Tipo", (c) -> {
                        if (c instanceof CargoExecutivoBean)
                            return "Executivo";
                        if (c instanceof CargoGerencialBean)
                            return "Gerencial";
                        if (c instanceof CargoOperacionalBean)
                            return "Operacional";
                        return "Não identificado";
                    }),
                    new ColumnDeclaration<CargoBean, String>("Nome", (c) -> c.getNome()),
                    new ColumnDeclaration<CargoBean, String>("Salário nível 1", (c) -> NumberFormat.getCurrencyInstance().format(c.getSalarioNivel1())),
                    new ColumnDeclaration<CargoBean, String>("Salário nível 2", (c) -> NumberFormat.getCurrencyInstance().format(c.getSalarioNivel2())),
                    new ColumnDeclaration<CargoBean, String>("Salário nível 3", (c) -> NumberFormat.getCurrencyInstance().format(c.getSalarioNivel3())),
                    new ColumnDeclaration<CargoBean, BigDecimal>("Salários de bonificação", (c) -> c.getPercentualSalarioBonus())
            );
    }

    @Override
    protected List<ColumnDeclaration<CargoBean, ?>> getColumns() {
        return columns;
    }
}
