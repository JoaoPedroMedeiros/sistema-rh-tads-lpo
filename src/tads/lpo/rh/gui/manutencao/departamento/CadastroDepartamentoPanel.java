package tads.lpo.rh.gui.manutencao.departamento;

import tads.lpo.rh.bean.DepartamentoBean;
import tads.lpo.rh.dao.CrudDAO;
import tads.lpo.rh.dao.DepartamentoDAO;
import tads.lpo.rh.gui.MDIFrame;
import tads.lpo.rh.gui._common.CadastroGenericoModal;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class CadastroDepartamentoPanel extends CadastroGenericoPanel<DepartamentoBean> {

    public CadastroDepartamentoPanel(MDIFrame frame) {
        super(frame);

        getTable().getColumnModel().getColumn(0).setMaxWidth(80);

    }

    @Override
    protected TableModelEditAndDelete<DepartamentoBean> getEditAndDeleteTableModel() {
        return new CadastroDepartamentoTableModel();
    }

    @Override
    protected List<ColumnDeclaration<DepartamentoBean, ? extends Comparable>> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<DepartamentoBean, String>("Nome", (d) -> d.getNome()),
                new ColumnDeclaration<DepartamentoBean, Integer>("ID", (d) -> d.getId())
        );
    }

    @Override
    protected CadastroGenericoModal<DepartamentoBean> criarModalNovo() {
        return new CadastroDepartamentoModal();
    }

    @Override
    protected CadastroGenericoModal<DepartamentoBean> criarModalEditar(DepartamentoBean target) {
        return new CadastroDepartamentoModal(target);
    }

    @Override
    protected CrudDAO<DepartamentoBean> getDAO() {
        return new DepartamentoDAO();
    }

    @Override
    protected Dimension modalDimensions() {
        return CadastroDepartamentoModal.dimensions;
    }
}
