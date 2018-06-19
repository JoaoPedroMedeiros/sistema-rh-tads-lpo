package tads.lpo.rh.bean;

import java.math.BigDecimal;

public class CargoExecutivoBean extends CargoBean {

    private BigDecimal bonificacaoPorDepartamento;

    public CargoExecutivoBean() {
        this.bonificacaoPorDepartamento = bonificacaoPorDepartamento;
    }

    public CargoExecutivoBean(String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        super(nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);
    }

    public CargoExecutivoBean(Integer id, String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        super(id, nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);
    }

    public BigDecimal getBonificacaoPorDepartamento() {
        return bonificacaoPorDepartamento;
    }

    public void setBonificacaoPorDepartamento(BigDecimal bonificacaoPorDepartamento) {
        this.bonificacaoPorDepartamento = bonificacaoPorDepartamento;
    }
}

