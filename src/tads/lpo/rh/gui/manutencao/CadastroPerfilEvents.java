package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.bean.PerfilBean;
import tads.lpo.rh.bean.SistemaBean;
import tads.lpo.rh.gui._common.CadastroGenericoEvents;

import java.util.List;

public interface CadastroPerfilEvents extends CadastroGenericoEvents<PerfilBean> {

    List<FuncionarioBean> listarFuncionarios();

    List<SistemaBean> listarSistemas();
}
