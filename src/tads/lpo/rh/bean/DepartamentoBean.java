package tads.lpo.rh.bean;


/**
 * Classe com as informações dos departamentos
 */
public class DepartamentoBean extends BaseBean {

    private String nome;

    private Integer contagemPessoas;

    public DepartamentoBean(Integer id, String nome) {
        super(id);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    // Utilizado na bonificação do gerente
    public Integer getContagemPessoas() {
        return contagemPessoas;
    }

    public void setContagemPessoas(Integer contagemPessoas) {
        this.contagemPessoas = contagemPessoas;
    }
}
