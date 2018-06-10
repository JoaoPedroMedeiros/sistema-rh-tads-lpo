package tads.lpo.rh.gui.manutencao.sistema;

import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.dao.CrudDAO;
import tads.lpo.rh.dao.SistemaDAO;
import tads.lpo.rh.gui._common.CadastroGenericoModal;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class CadastroSistemaPanel extends CadastroGenericoPanel<SistemaBean> {

    public CadastroSistemaPanel(Frame frame) {
        super(frame);

        getTable().getColumnModel().getColumn(0).setMaxWidth(80);

    }

    @Override
    protected TableModelEditAndDelete<SistemaBean> getEditAndDeleteTableModel() {
        return new CadastroSistemaTableModel();
    }

    @Override
    protected List<ColumnDeclaration<SistemaBean, ? extends Comparable>> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<SistemaBean, String>("Nome", (s) -> s.getNome()),
                new ColumnDeclaration<SistemaBean, Integer>("ID", (s) -> s.getId())
        );
    }

    @Override
    protected CadastroGenericoModal<SistemaBean> criarModalNovo() {
        return new CadastroSistemaModal();
    }

    @Override
    protected CadastroGenericoModal<SistemaBean> criarModalEditar(SistemaBean target) {
        return new CadastroSistemaModal(target);
    }

    @Override
    protected CrudDAO<SistemaBean> getDAO() {
        return new SistemaDAO();
    }

    @Override
    protected Dimension modalDimensions() {
        return CadastroSistemaModal.dimensions;
    }
}
