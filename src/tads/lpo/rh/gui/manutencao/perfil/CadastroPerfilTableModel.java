package tads.lpo.rh.gui.manutencao.perfil;

import tads.lpo.rh.bean.PerfilBean;
import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.gui._common.tablemodel.ColumnDeclaration;
import tads.lpo.rh.gui._common.tablemodel.TableModelEditAndDelete;

import java.util.Arrays;
import java.util.List;

public class CadastroPerfilTableModel extends TableModelEditAndDelete<PerfilBean> {

    private final List<ColumnDeclaration<PerfilBean, ?>> columns;

    public CadastroPerfilTableModel() {
        columns =
            Arrays.asList(
                new ColumnDeclaration<PerfilBean, Integer>("ID", (d) -> d.getId()),
                new ColumnDeclaration<PerfilBean, String>("Nome", (d) -> d.getFuncionario().getNome()),
                new ColumnDeclaration<PerfilBean, String>("CPF", (d) -> d.getFuncionario().getCpf()),
                new ColumnDeclaration<PerfilBean, String>("Sistemas", (d) -> {
                    String a = "";
                    if (d.getSistemas() != null)
                        for (SistemaBean s : d.getSistemas()) {
                            a = a.concat(s.getNome() + "; ");
                        }

                    return a.equals("") ? "Nenhum sistema" : a;
                })
            );
    }

    @Override
    protected List<ColumnDeclaration<PerfilBean, ?>> getColumns() {
        return columns;
    }
}
