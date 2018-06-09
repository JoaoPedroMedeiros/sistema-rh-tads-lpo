package tads.lpo.rh.bean;

import tads.lpo.rh.gui.login.Autenticavel;

import java.math.BigDecimal;
import java.util.List;

public abstract class FuncionarioBean<C extends CargoBean> extends BaseBean implements Autenticavel {

    private C cargo;

    private String nome;

    private String sobrenome;

    private String rg;

    private String cpf;

    private String telefone;

    private Integer nivel;

    private String senha;

    private List<SistemaBean> sistemas;

    public FuncionarioBean(C cargo) {
        this.cargo = cargo;
    }

    public FuncionarioBean(Integer id, C cargo, String nome, String sobrenome, String rg, String cpf, String telefone, Integer nivel, String senha) {
        super(id);
        this.cargo = cargo;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.cpf = cpf;
        this.telefone = telefone;
        this.nivel = nivel;
        this.senha = senha;
    }

    public BigDecimal getSalario() {
        if (getCargo() != null && getNivel() != null) {
            switch (getNivel()) {
                case 1:
                    return getCargo().getSalarioNivel1();
                case 2:
                    return getCargo().getSalarioNivel2();
                case 3:
                    return getCargo().getSalarioNivel3();
                default:
                    return null;
            }
        }
        return null;
    }

    public BigDecimal getBonificacaoAnual() {
        if (getCargo() != null) {
            BigDecimal salario = getSalario();
            BigDecimal percentual = getCargo().getPercentualSalarioBonus();

            if (percentual != null && salario != null) {
                return percentual.multiply(getSalario()).setScale(2);
            }
        }
        return null;
    }

    @Override
    public boolean autenticar(SistemaBean sistema, String cpf, String senha) {
        return validarUsuarioSenha(cpf, senha) && getSistemas() != null && getSistemas().contains(sistema);
    }

    protected boolean validarUsuarioSenha(String cpf, String senha) {
        return cpf.equals(getCpf().replace(".", "").replace("-", "")) &&
                senha.equals(getSenha());
    }

    public C getCargo() {
        return this.cargo;
    }

    public void setCargo(C cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public List<SistemaBean> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<SistemaBean> sistemas) {
        this.sistemas = sistemas;
    }

    @Override
    public String toString() {
        return this.getNome() + " " + this.getSobrenome();
    }
}
