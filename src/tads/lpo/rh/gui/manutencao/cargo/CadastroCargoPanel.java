package tads.lpo.rh.gui.manutencao.cargo;

import tads.lpo.rh.bean.*;
import tads.lpo.rh.dao.CargoDAO;
import tads.lpo.rh.dao.CrudDAO;
import tads.lpo.rh.gui.MDIFrame;
import tads.lpo.rh.gui._common.CadastroGenericoModal;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CadastroCargoPanel extends CadastroGenericoPanel<CargoBean> {

    public CadastroCargoPanel(MDIFrame frame) {
        super(frame);
    }

    @Override
    protected TableModelEditAndDelete getEditAndDeleteTableModel() {
        return new CadastroCargoTableModel();
    }

    @Override
    protected List<ColumnDeclaration<CargoBean, ? extends Comparable>> getSortColumns() {
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

    @Override
    protected CadastroGenericoModal criarModalNovo() {
        return new CadastroCargoModal();
    }

    @Override
    protected CadastroGenericoModal criarModalEditar(CargoBean target) {
        return new CadastroCargoModal(target);
    }

    @Override
    protected CrudDAO getDAO() {
        return new CargoDAO();
    }

    @Override
    protected Dimension modalDimensions() {
        return CadastroCargoModal.dimensions;
    }
}
