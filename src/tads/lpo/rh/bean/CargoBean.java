package tads.lpo.rh.bean;


import java.math.BigDecimal;

public abstract class CargoBean extends BaseBean {

    private String nome;

    private BigDecimal salarioNivel1;

    private BigDecimal salarioNivel2;

    private BigDecimal salarioNivel3;

    private BigDecimal percentualSalarioBonus;

    public CargoBean() {
        super();
    }

    public CargoBean(String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        this.nome = nome;
        this.salarioNivel1 = salarioNivel1;
        this.salarioNivel2 = salarioNivel2;
        this.salarioNivel3 = salarioNivel3;
        this.percentualSalarioBonus = percentualSalarioBonus;
    }

    public CargoBean(Integer id, String nome, BigDecimal salarioNivel1, BigDecimal salarioNivel2, BigDecimal salarioNivel3, BigDecimal percentualSalarioBonus) {
        super(id);
        this.nome = nome;
        this.salarioNivel1 = salarioNivel1;
        this.salarioNivel2 = salarioNivel2;
        this.salarioNivel3 = salarioNivel3;
        this.percentualSalarioBonus = percentualSalarioBonus;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalarioNivel1() {
        return salarioNivel1;
    }

    public void setSalarioNivel1(BigDecimal salarioNivel1) {
        this.salarioNivel1 = salarioNivel1;
    }

    public BigDecimal getSalarioNivel2() {
        return salarioNivel2;
    }

    public void setSalarioNivel2(BigDecimal salarioNivel2) {
        this.salarioNivel2 = salarioNivel2;
    }

    public BigDecimal getSalarioNivel3() {
        return salarioNivel3;
    }

    public void setSalarioNivel3(BigDecimal salarioNivel3) {
        this.salarioNivel3 = salarioNivel3;
    }

    public BigDecimal getPercentualSalarioBonus() {
        return percentualSalarioBonus;
    }

    public void setPercentualSalarioBonus(BigDecimal percentualSalarioBonus) {
        this.percentualSalarioBonus = percentualSalarioBonus;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
