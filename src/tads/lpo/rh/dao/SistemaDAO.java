package tads.lpo.rh.dao;

import tads.lpo.rh.ConnectionFactory;
import tads.lpo.rh.bean.DepartamentoBean;
import tads.lpo.rh.bean.SistemaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaDAO extends BaseDAO<SistemaBean> {

    public static final SistemaBean SISTEMA1 = new SistemaBean(1, "Sistema 1");

    public static final SistemaBean SISTEMA2 = new SistemaBean(2, "Sistema 2");

    private final static List<SistemaBean> sistemas =
            new ArrayList<>(Arrays.asList(
                    SISTEMA1,
                    SISTEMA2
            ));

    private static int i = 2;

    public void cadastrar(SistemaBean sistema) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Sistema (nome) VALUES (?)"
            );
            statement.setString(1, sistema.getNome());
            statement.executeUpdate();

        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void alterar(SistemaBean sistema) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Sistema set nome = ? WHERE id = ?"
            );

            statement.setString(1, sistema.getNome());
            statement.setInt(2, sistema.getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void excluir(SistemaBean sistema) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Sistema WHERE id = ?"
            );

            statement.setInt(1, sistema.getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public List<SistemaBean> listarTodos(String filtro) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            String sql = "SELECT * FROM Sistema";
            sql = sql + (filtro == null || filtro.isEmpty() ? "" : " WHERE nome like ?");

            PreparedStatement statement = connection.prepareStatement(sql);

            if (filtro != null && !filtro.isEmpty()) {
                statement.setString(1, "%" + filtro + "%");
            }

            ResultSet rs = statement.executeQuery();

            List<SistemaBean> sistemas = new ArrayList<>();

            while (rs.next()) {
                SistemaBean sistema = new SistemaBean(
                        rs.getInt("id"),
                        rs.getString("nome")

                );
                sistemas.add(sistema);

            }
            return sistemas;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }
}
