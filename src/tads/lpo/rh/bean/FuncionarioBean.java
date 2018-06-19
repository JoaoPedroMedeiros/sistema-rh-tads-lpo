package tads.lpo.rh.bean;

import tads.lpo.rh.gui.login.Autenticavel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Executivo/Gerencial/Operacional herdam funcionário
 * porém é necessário informar em cada uma delas qual o tipo do cargo que será herdado
 * (isso é especificado através através do generic C).
 */
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

    /**
     * Esse construtor especifica o cargo sem precisar passar todos os outros atributos do outro constrtutor
     */
    public FuncionarioBean(C cargo) {
        this.cargo = cargo;
    }

    /**
     * Construtor da classe generica
     */
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

    /**
     * Esse método calcula o salário de acordo com o cargo e o nível
     * Obs: BigDecimal é recomendado para trabalhar com dinheiro (tipos mais precisos)
     */
    public BigDecimal getSalario() {
        // Valida se está sendo passado cargo e nível
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

    /**
     * Este método calcula o bonus de acordo com o cargo
     */
    public BigDecimal getBonificacaoAnual() {
        //Validação do cargo
        if (getCargo() != null) {
            BigDecimal salario = getSalario();
            BigDecimal percentual = getCargo().getPercentualSalarioBonus();
            //Validação do salário
            if (percentual != null && salario != null) {
                //Pega o salário x pelo percentual e exibe com apenas 2 casas após a vírgula
                return percentual.multiply(getSalario()).setScale(2);
            }
        }
        return null;
    }

    @Override
    /**
     * Este método valida o usuário, a senha e o sistema, porém como a classe de funcionarios executivos não
     * precisa verificar o sistema, visto que o Diretor pode acessar todos, esse método é reescrito.
     */
    public boolean autenticar(SistemaBean sistema, String cpf, String senha) {
        //Valida se na lista de sistemas está contém o sistema no qual o funcionário deseja logar
        return validarUsuarioSenha(cpf, senha) && getSistemas() != null && getSistemas().contains(sistema);
    }

    /**
     * ESte método valida se o cpf e a senha são os mesmos que foram setados (que estão contidos no banco)
     */
    protected boolean validarUsuarioSenha(String cpf, String senha) {
        return cpf.equals(getCpf().replace(".", "").replace("-", "")) &&
                senha.equals(getSenha());
    }

    /**
     * Abaixo todos os getters e setters
     */
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
