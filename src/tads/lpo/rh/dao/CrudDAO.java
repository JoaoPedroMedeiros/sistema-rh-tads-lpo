package tads.lpo.rh.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<B> {

    void cadastrar(B bean) throws SQLException;

    void alterar(B bean) throws SQLException;

    void excluir(B bean) throws SQLException;

    List<B> listarTodos(String filtro) throws SQLException;
}
