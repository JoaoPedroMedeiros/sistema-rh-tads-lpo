package tads.lpo.rh.gui._common;

import java.util.List;

public interface CadastroGenericoEvents<T> extends ModalEvents<T> {

    List<T> listar(String filtro);

    void novo(T target);

    void editar(T target);

    void excluir(T target);
}
