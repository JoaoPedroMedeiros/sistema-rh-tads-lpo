package tads.lpo.rh.bean;

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

    public Integer getContagemPessoas() {
        return contagemPessoas;
    }

    public void setContagemPessoas(Integer contagemPessoas) {
        this.contagemPessoas = contagemPessoas;
    }
}
