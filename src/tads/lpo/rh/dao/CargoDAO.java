package tads.lpo.rh.dao;

import tads.lpo.rh.ConnectionFactory;
import tads.lpo.rh.bean.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CargoDAO extends BaseDAO<CargoBean> {

    private static int i = 4;

    public void cadastrar(CargoBean cargo) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Cargo (\n" +
                "  tipo                      ,\n" +
                "  nome                      ,\n" +
                "  salarioNivel1             ,\n" +
                "  salarioNivel2             ,\n" +
                "  salarioNivel3             ,\n" +
                "  percentualSalarioBonus    ,\n" +
                "  bonificacaoPorDepartamento,\n" +
                "  bonificacaoPorPessoa      ,\n" +
                "  acessaSistema             \n" +
                ")\n" +
                "VALUES (\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?\n" +
                ")"
            );

            int tipo;

            if (cargo instanceof CargoOperacionalBean) {
                tipo = 1;
            }
            else if (cargo instanceof CargoGerencialBean) {
                tipo = 2;
            }
            else {
                tipo = 3;
            }

            statement.setInt(1, tipo);
            statement.setString(2, cargo.getNome());
            statement.setBigDecimal(3, cargo.getSalarioNivel1());
            statement.setBigDecimal(4, cargo.getSalarioNivel2());
            statement.setBigDecimal(5, cargo.getSalarioNivel3());
            statement.setBigDecimal(6, cargo.getPercentualSalarioBonus());

            if (cargo instanceof CargoExecutivoBean)
                statement.setBigDecimal(7, ((CargoExecutivoBean) cargo).getBonificacaoPorDepartamento());
            else
                statement.setNull(7, Types.DECIMAL);

            if (cargo instanceof CargoGerencialBean)
                statement.setBigDecimal(8, ((CargoGerencialBean) cargo).getBonificacaoPorPessoa());
            else
                statement.setNull(8, Types.DECIMAL);

            if (cargo instanceof CargoOperacionalBean)
                statement.setBoolean(9, ((CargoOperacionalBean) cargo).getAcessaSistema());
            else
                statement.setNull(9, Types.DECIMAL);

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void alterar(CargoBean cargo) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
            "UPDATE Cargo SET\n" +
                "  tipo                       = ?,\n" +
                "  nome                       = ?,\n" +
                "  salarioNivel1              = ?,\n" +
                "  salarioNivel2              = ?,\n" +
                "  salarioNivel3              = ?,\n" +
                "  percentualSalarioBonus     = ?,\n" +
                "  bonificacaoPorDepartamento = ?,\n" +
                "  bonificacaoPorPessoa       = ?,\n" +
                "  acessaSistema              = ?\n" +
                "WHERE\n" +
                "  id = ?"
            );
            int tipo;

            if (cargo instanceof CargoOperacionalBean) {
                tipo = 1;
            }
            else if (cargo instanceof CargoGerencialBean) {
                tipo = 2;
            }
            else {
                tipo = 3;
            }

            statement.setInt(1, tipo);
            statement.setString(2, cargo.getNome());
            statement.setBigDecimal(3, cargo.getSalarioNivel1());
            statement.setBigDecimal(4, cargo.getSalarioNivel2());
            statement.setBigDecimal(5, cargo.getSalarioNivel3());
            statement.setBigDecimal(6, cargo.getPercentualSalarioBonus());

            if (cargo instanceof CargoExecutivoBean)
                statement.setBigDecimal(7, ((CargoExecutivoBean) cargo).getBonificacaoPorDepartamento());
            else
                statement.setNull(7, Types.DECIMAL);

            if (cargo instanceof CargoGerencialBean)
                statement.setBigDecimal(8, ((CargoGerencialBean) cargo).getBonificacaoPorPessoa());
            else
                statement.setNull(8, Types.DECIMAL);

            if (cargo instanceof CargoOperacionalBean)
                statement.setBoolean(9, ((CargoOperacionalBean) cargo).getAcessaSistema());
            else
                statement.setNull(9, Types.DECIMAL);

            statement.setInt(10, cargo.getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void excluir(CargoBean cargo) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Cargo WHERE id = ?"
            );

            statement.setInt(1, cargo.getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public List<CargoBean> listarTodos(String filtro) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            String sql =
                "SELECT\n" +
                "  id                          ,\n" +
                "  tipo                        ,\n" +
                "  nome                        ,\n" +
                "  salarioNivel1               ,\n" +
                "  salarioNivel2               ,\n" +
                "  salarioNivel3               ,\n" +
                "  percentualSalarioBonus      ,\n" +
                "  bonificacaoPorDepartamento  ,\n" +
                "  bonificacaoPorPessoa        ,\n" +
                "  acessaSistema               \n" +
                "FROM\n" +
                "  Cargo";

            sql = sql + (filtro == null || filtro.isEmpty() ? "" : " WHERE nome like ? ");

            PreparedStatement statement = connection.prepareStatement(sql);

            if (filtro != null && !filtro.isEmpty()) {
                statement.setString(1, "%" + filtro + "%");
            }

            ResultSet rs = statement.executeQuery();

            List<CargoBean> cargos = new ArrayList<>();

            while (rs.next()) {
                CargoBean cargo;

                int tipo = rs.getInt("tipo");

                // Operacional
                if (tipo == 1) {
                    cargo = new CargoOperacionalBean();
                    ((CargoOperacionalBean) cargo).setAcessaSistema(rs.getBoolean("acessaSistema"));
                }
                // Gerencial
                else if (tipo == 2) {
                    cargo = new CargoGerencialBean();
                    ((CargoGerencialBean) cargo).setBonificacaoPorPessoa(rs.getBigDecimal("bonificacaoPorPessoa"));
                }
                // Executivo
                else {
                    cargo = new CargoExecutivoBean();
                    ((CargoExecutivoBean) cargo).setBonificacaoPorDepartamento(rs.getBigDecimal("bonificacaoPorDepartamento"));
                }

                cargo.setId(rs.getInt("id"));
                cargo.setNome(rs.getString("nome"));
                cargo.setSalarioNivel1(rs.getBigDecimal("salarioNivel1"));
                cargo.setSalarioNivel2(rs.getBigDecimal("salarioNivel2"));
                cargo.setSalarioNivel3(rs.getBigDecimal("salarioNivel3"));
                cargo.setPercentualSalarioBonus(rs.getBigDecimal("percentualSalarioBonus"));

                cargos.add(cargo);
            }

            return cargos;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }
}
