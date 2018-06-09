package tads.lpo.rh.bean;

import java.util.List;

public class PerfilBean extends BaseBean {

    private FuncionarioBean funcionario;

    private List<SistemaBean> sistemas;

    public PerfilBean(FuncionarioBean funcionario, List<SistemaBean> sistemas) {
        this.funcionario = funcionario;
        this.sistemas = sistemas;
    }

    public PerfilBean(Integer id, FuncionarioBean funcionario, List<SistemaBean> sistemas) {
        super(id);
        this.funcionario = funcionario;
        this.sistemas = sistemas;
    }

    public FuncionarioBean getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioBean funcionario) {
        this.funcionario = funcionario;
    }

    public List<SistemaBean> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<SistemaBean> sistemas) {
        this.sistemas = sistemas;
    }
}
