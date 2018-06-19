package tads.lpo.rh.bean;

import java.math.BigDecimal;

public class CargoGerencialBean extends CargoBean {

    private BigDecimal bonificacaoPorPessoa;

    public CargoGerencialBean() {

    }

    public CargoGerencialBean(String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        super(nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);
    }

    public CargoGerencialBean(Integer id, String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        super(id, nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);
    }

    public BigDecimal getBonificacaoPorPessoa() {
        return bonificacaoPorPessoa;
    }

    public void setBonificacaoPorPessoa(BigDecimal bonificacaoPorPessoa) {
        this.bonificacaoPorPessoa = bonificacaoPorPessoa;
    }
}
