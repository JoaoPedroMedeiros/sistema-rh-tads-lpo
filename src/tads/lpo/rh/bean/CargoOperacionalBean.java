package tads.lpo.rh.bean;

import java.math.BigDecimal;

public class CargoOperacionalBean extends CargoBean {

    private Boolean acessaSistema;

    public CargoOperacionalBean() {
    }

    public CargoOperacionalBean(String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        super(nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);
    }

    public CargoOperacionalBean(Integer id, String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        super(id, nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);
    }

    public Boolean getAcessaSistema() {
        return acessaSistema;
    }

    public void setAcessaSistema(Boolean acessaSistema) {
        this.acessaSistema = acessaSistema;
    }
}
