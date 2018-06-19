package tads.lpo.rh.bean;

/**
 * Classe do chão de fábrica
 */
public class FuncionarioOperacionalBean extends FuncionarioBean<CargoOperacionalBean> {

    private DepartamentoBean departamentoAtuacao;

    public FuncionarioOperacionalBean(CargoOperacionalBean cargo) {
        super(cargo);
    }

    public FuncionarioOperacionalBean(Integer id, CargoOperacionalBean cargo, String nome, String sobrenome, String rg, String cpf, String telefone, Integer nivel,
                                      String senha, DepartamentoBean departamentoAtuacao) {

        super(id, cargo, nome, sobrenome, rg, cpf, telefone, nivel,senha);
        this.departamentoAtuacao = departamentoAtuacao;
    }

    public DepartamentoBean getDepartamentoAtuacao() {
        return departamentoAtuacao;
    }

    public void setDepartamentoAtuacao(DepartamentoBean departamentoAtuacao) {
        this.departamentoAtuacao = departamentoAtuacao;
    }

    @Override

    // Aqui que verifica se o auxiliar de limpeza pode acessar algum sistema
    public boolean autenticar(SistemaBean sistema, String cpf, String senha) {
        if (getCargo() != null && getCargo().getAcessaSistema() != null && !getCargo().getAcessaSistema())
            return false;

        return super.autenticar(sistema, cpf, senha);
    }
}
