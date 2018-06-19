package tads.lpo.rh.bean;

import java.math.BigDecimal;


//
public class CargoOperacionalBean extends CargoBean {

    private Boolean acessaSistema;

    public CargoOperacionalBean() {
    }

    public CargoOperacionalBean(String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus,
                                Boolean acessaSistema) {
        super(nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);

        this.acessaSistema = acessaSistema;
    }

    public CargoOperacionalBean(Integer id, String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus,
                                Boolean acessaSistema) {
        super(id, nome, salarioNivel1, salarioNivel2, salarioNivel3, percentualSalarioBonus);

        this.acessaSistema = acessaSistema;
    }

    public Boolean getAcessaSistema() {
        return acessaSistema;
    }

    public void setAcessaSistema(Boolean acessaSistema) {
        this.acessaSistema = acessaSistema;
    }
}
