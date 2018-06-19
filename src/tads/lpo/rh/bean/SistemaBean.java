package tads.lpo.rh.bean;

/**
 * ESta classe cria um id para cada sistema
 */
public class SistemaBean extends BaseBean {

    private String nome;

    public SistemaBean() {}

    public SistemaBean(Integer id, String nome) {
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
        return getNome();
    }
}
