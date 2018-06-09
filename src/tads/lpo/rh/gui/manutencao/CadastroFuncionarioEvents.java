package tads.lpo.rh.gui.manutencao;

import tads.lpo.rh.bean.CargoBean;
import tads.lpo.rh.bean.DepartamentoBean;
import tads.lpo.rh.bean.FuncionarioBean;
import tads.lpo.rh.gui._common.CadastroGenericoEvents;

import java.util.List;

public interface CadastroFuncionarioEvents extends CadastroGenericoEvents<FuncionarioBean> {

    List<DepartamentoBean> listarDepartamentos();

    List<CargoBean> listarCargos();
}
