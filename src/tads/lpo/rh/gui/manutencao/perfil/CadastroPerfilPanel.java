package tads.lpo.rh.gui.manutencao.perfil;

import tads.lpo.rh.bean.PerfilBean;
import tads.lpo.rh.dao.CrudDAO;
import tads.lpo.rh.dao.PerfilDAO;
import tads.lpo.rh.gui._common.CadastroGenericoModal;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class CadastroPerfilPanel extends CadastroGenericoPanel<PerfilBean> {

    public CadastroPerfilPanel(Frame frame) {
        super(frame);

        getTable().getColumnModel().getColumn(0).setMaxWidth(80);

    }

    @Override
    protected TableModelEditAndDelete<PerfilBean> getEditAndDeleteTableModel() {
        return new CadastroPerfilTableModel();
    }

    @Override
    protected List<ColumnDeclaration<PerfilBean, ? extends Comparable>> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<PerfilBean, String>("Nome", (d) -> d.getFuncionario().getNome()),
                new ColumnDeclaration<PerfilBean, Integer>("ID", (d) -> d.getId())
        );
    }

    @Override
    protected CadastroGenericoModal<PerfilBean> criarModalNovo() {
        return new CadastroPerfilModal();
    }

    @Override
    protected CadastroGenericoModal<PerfilBean> criarModalEditar(PerfilBean target) {
        return new CadastroPerfilModal(target);
    }

    @Override
    protected CrudDAO<PerfilBean> getDAO() {
        return new PerfilDAO();
    }

    @Override
    protected Dimension modalDimensions() {
        return CadastroPerfilModal.dimensions;
    }
}
