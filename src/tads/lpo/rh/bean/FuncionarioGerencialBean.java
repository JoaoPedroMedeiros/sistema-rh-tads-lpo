package tads.lpo.rh.bean;

import java.math.BigDecimal;

/**
 * Classe do Gerente
 */

public class FuncionarioGerencialBean extends FuncionarioBean<CargoGerencialBean> {

    private DepartamentoBean departamentoGerenciado;

    public FuncionarioGerencialBean(CargoGerencialBean cargo) {
        super(cargo);
    }

    public FuncionarioGerencialBean(Integer id, CargoGerencialBean cargo, String nome, String sobrenome, String rg, String cpf, String telefone, Integer nivel, String senha, DepartamentoBean departamentoGerenciado) {
        super(id, cargo, nome, sobrenome, rg, cpf, telefone, nivel, senha);
        this.departamentoGerenciado = departamentoGerenciado;
    }

    @Override
    public BigDecimal getBonificacaoAnual() {
        BigDecimal bonificacao = super.getBonificacaoAnual();

        if (bonificacao != null && getDepartamentoGerenciado() != null && getDepartamentoGerenciado().getContagemPessoas() != null && getCargo() != null &&
                getCargo().getBonificacaoPorPessoa() != null) {

            BigDecimal commisaoDepartamento = getCargo().getBonificacaoPorPessoa().multiply(BigDecimal.valueOf(getDepartamentoGerenciado().getContagemPessoas()));

            return bonificacao.add(commisaoDepartamento);
        }
        return bonificacao;
    }

    public DepartamentoBean getDepartamentoGerenciado() {
        return departamentoGerenciado;
    }

    public void setDepartamentoGerenciado(DepartamentoBean departamentoGerenciado) {
        this.departamentoGerenciado = departamentoGerenciado;
    }
}
