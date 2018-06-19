package tads.lpo.rh.dao;

import tads.lpo.rh.ConnectionFactory;
import tads.lpo.rh.bean.DepartamentoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DepartamentoDAO extends BaseDAO<DepartamentoBean> {

    public static final DepartamentoBean DEPARTAMENTO1 = new DepartamentoBean(1, "Departamento 1");
    public static final DepartamentoBean DEPARTAMENTO2 = new DepartamentoBean(2, "Departamento 2");

    private final static List<DepartamentoBean> departamentos =
            new ArrayList<DepartamentoBean>(Arrays.asList(DEPARTAMENTO1, DEPARTAMENTO2));

    private static int i = 2;

    public void cadastrar(DepartamentoBean departamento) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Departamento (nome) VALUES (?)"
            );
            statement.setString(1, departamento.getNome());
            statement.executeUpdate();

        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void alterar(DepartamentoBean departamento) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
            "UPDATE Departamento set nome = ? WHERE id = ?"
            );

            statement.setString(1, departamento.getNome());
            statement.setInt(2, departamento.getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void excluir(DepartamentoBean departamento) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Departamento WHERE id = ?"
            );

            statement.setInt(1, departamento.getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public List<DepartamentoBean> listarTodos(String filtro) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            String sql = "SELECT * FROM Departamento";
            sql = sql + (filtro == null || filtro.isEmpty() ? "" : " WHERE nome like ?");

            PreparedStatement statement = connection.prepareStatement(sql);

            if (filtro != null && !filtro.isEmpty()) {
                statement.setString(1, "%" + filtro + "%");
            }

            ResultSet rs = statement.executeQuery();

            List<DepartamentoBean> departamentos = new ArrayList<>();

            while (rs.next()) {
                DepartamentoBean departamento = new DepartamentoBean(
                        rs.getInt("id"),
                        rs.getString("nome")

                );
                departamentos.add(departamento);

            }
            return departamentos;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }
}
