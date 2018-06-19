package tads.lpo.rh.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe do Diretor
 */
public class FuncionarioExecutivoBean extends FuncionarioBean<CargoExecutivoBean> {

    private List<DepartamentoBean> departamentos;

    public FuncionarioExecutivoBean(CargoExecutivoBean cargo) {
        super(cargo);
    }

    public FuncionarioExecutivoBean(Integer id, CargoExecutivoBean cargo, String nome, String sobrenome, String rg, String cpf, String telefone, Integer nivel, String senha, List<DepartamentoBean> departamentos) {
        super(id, cargo, nome, sobrenome, rg, cpf, telefone, nivel, senha);
        this.departamentos = departamentos;
    }

    @Override
    public BigDecimal getBonificacaoAnual() {
        BigDecimal bonificacao = super.getBonificacaoAnual();

        if (bonificacao != null && getDepartamentos() != null && getCargo() != null && getCargo().getBonificacaoPorDepartamento() != null) {
            BigDecimal commisaoDepartamento = getCargo().getBonificacaoPorDepartamento().multiply(BigDecimal.valueOf(departamentos.size()));

            return bonificacao.add(commisaoDepartamento);
        }
        return bonificacao;
    }

    @Override
    public boolean autenticar(SistemaBean sistema, String cpf, String senha) {
        return validarUsuarioSenha(cpf, senha);
    }

    public List<DepartamentoBean> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<DepartamentoBean> departamentos) {
        this.departamentos = departamentos;
    }
}
