package tads.lpo.rh.dao;

import tads.lpo.rh.ConnectionFactory;
import tads.lpo.rh.bean.CargoOperacionalBean;
import tads.lpo.rh.bean.FuncionarioOperacionalBean;
import tads.lpo.rh.bean.PerfilBean;
import tads.lpo.rh.bean.SistemaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilDAO extends BaseDAO<PerfilBean> {

    public void cadastrar(PerfilBean perfil) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            for (SistemaBean sistema : perfil.getSistemas()) {
                PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO AcessosFuncionario (idFuncionario, idSistema) VALUES (?, ?)"
                );
                statement.setInt(1, perfil.getFuncionario().getId());
                statement.setInt(2, sistema.getId());
                statement.executeUpdate();
            }
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void alterar(PerfilBean perfil) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM AcessosFuncionario WHERE idFuncionario = ?"
            );

            statement.setInt(1, perfil.getFuncionario().getId());

            statement.executeUpdate();

            for (SistemaBean sistema : perfil.getSistemas()) {
                PreparedStatement stsSistema = connection.prepareStatement(
                        "INSERT INTO AcessosFuncionario (idFuncionario, idSistema) VALUES (?, ?)"
                );
                stsSistema.setInt(1, perfil.getFuncionario().getId());
                stsSistema.setInt(2, sistema.getId());
                stsSistema.executeUpdate();
            }

            connection.commit();
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void excluir(PerfilBean perfil) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM AcessosFuncionario WHERE idFuncionario = ?"
            );

            statement.setInt(1, perfil.getFuncionario().getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public List<PerfilBean> listarTodos(String filtro) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            String s1 = "SELECT\n" +
                    "  AcessosFuncionario.id idPerfil,\n" +
                    "  idFuncionario        ,\n" +
                    "  idSistema            ,\n" +
                    "  Funcionario.nome fnome,\n" +
                    "  Funcionario.sobrenome,\n" +
                    "  Funcionario.cpf,      \n" +
                    "  Sistema.nome snome    \n" +
                    "FROM\n" +
                    "  AcessosFuncionario\n" +
                    "  INNER JOIN Funcionario ON (AcessosFuncionario.idFuncionario = Funcionario.id)\n" +
                    "  INNER JOIN Sistema ON (AcessosFuncionario.idSistema = Sistema.id)";

            s1 = s1 + (filtro == null || filtro.isEmpty() ? "" :
                    "WHERE Funcionario.nome like ?");

            s1 = s1 + " ORDER BY idFuncionario";

            PreparedStatement statement = connection.prepareStatement(s1);

            if (filtro != null && !filtro.isEmpty())
                statement.setString(1, "%" + filtro + "%");

            ResultSet rs = statement.executeQuery();

            List<PerfilBean> perfis = new ArrayList<>();

            PerfilBean perfil = new PerfilBean();

            boolean trocou = false;

            while (rs.next()) {
                trocou = perfil.getFuncionario() == null || perfil.getFuncionario().getId() != rs.getInt("idFuncionario");

                perfil = trocou ? new PerfilBean() : perfil;

                if (trocou)
                    perfil.setId(rs.getInt("idPerfil"));

                if (perfil.getFuncionario() == null) {
                    FuncionarioOperacionalBean funcionario = new FuncionarioOperacionalBean(new CargoOperacionalBean());
                    funcionario.setId(rs.getInt("idFuncionario"));
                    funcionario.setNome(rs.getString("fnome"));
                    funcionario.setCpf(rs.getString("cpf"));
                    perfil.setFuncionario(funcionario);
                }

                if (perfil.getSistemas() == null)
                    perfil.setSistemas(new ArrayList<>());

                SistemaBean s = new SistemaBean();
                s.setId(rs.getInt("idSistema"));
                s.setNome(rs.getString("snome"));
                perfil.getSistemas().add(s);

                if (trocou)
                    perfis.add(perfil);
            }

            return perfis;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }
}
