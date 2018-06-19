package tads.lpo.rh.gui.manutencao.funcionario;

import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.bean.FuncionarioExecutivoBean;
import tads.lpo.rh.dao.CrudDAO;
import tads.lpo.rh.dao.FuncionarioDAO;
import tads.lpo.rh.gui.MDIFrame;
import tads.lpo.rh.gui._common.CadastroGenericoModal;
import tads.lpo.rh.gui._common.CadastroGenericoPanel;
import tads.lpo.rh.gui._common.ErroFrame;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CadastroFuncionarioPanel extends CadastroGenericoPanel<FuncionarioBean> {

    public CadastroFuncionarioPanel(MDIFrame frame) {
        super(frame);
    }

    @Override
    protected TableModelEditAndDelete getEditAndDeleteTableModel() {
        return new CadastroFuncionarioTableModel();
    }

    @Override
    protected List<ColumnDeclaration<FuncionarioBean, ? extends Comparable>> getSortColumns() {
        return Arrays.asList(
                new ColumnDeclaration<FuncionarioBean, String>("Nome completo", (f) -> f.getNome()),
                new ColumnDeclaration<FuncionarioBean, BigDecimal>("Salário", (f) -> BigDecimal.ZERO)
        );
    }

    @Override
    protected void carregarInformacoesAdicionaisEdicao(FuncionarioBean funcionario) {
        if (funcionario instanceof FuncionarioExecutivoBean) {
            try {
                ((FuncionarioExecutivoBean) funcionario).setDepartamentos(
                        new FuncionarioDAO().buscarDepartamentosGerenciados(funcionario)
                );
            }
            catch (SQLException e) {
                ErroFrame.exibirErro(e);
            }
        }
    }

    @Override
    protected CadastroGenericoModal criarModalNovo() {
        return new CadastroFuncionarioModal();
    }

    @Override
    protected CadastroGenericoModal criarModalEditar(FuncionarioBean target) {
        return new CadastroFuncionarioModal(target);
    }

    @Override
    protected CrudDAO getDAO() {
        return new FuncionarioDAO();
    }

    @Override
    protected Dimension modalDimensions() {
        return CadastroFuncionarioModal.dimensions;
    }
}
