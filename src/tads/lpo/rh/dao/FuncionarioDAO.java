package tads.lpo.rh.dao;

import javafx.util.Callback;
import tads.lpo.rh.ConnectionFactory;
import tads.lpo.rh.bean.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class FuncionarioDAO extends BaseDAO<FuncionarioBean> {

    public void cadastrar(FuncionarioBean funcionario) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Funcionario (    \n" +
                "  nome                      ,\n" +
                "  sobrenome                 ,\n" +
                "  rg                        ,\n" +
                "  cpf                       ,\n" +
                "  telefone                  ,\n" +
                "  nivel                     ,\n" +
                "  senha                     ,\n" +
                "  cargoId                   ,\n" +
                "  idDepartamentoAtuacao     ,\n" +
                "  idDepartamentoGerenciado   \n" +
                ")                            \n" +
                "VALUES (                     \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?,                         \n" +
                "  ?                          \n" +
                ")                            \n",
                Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getSobrenome());
            statement.setString(3, funcionario.getRg());
            statement.setString(4, funcionario.getCpf());
            statement.setString(5, funcionario.getTelefone());
            statement.setInt(6, funcionario.getNivel());
            statement.setString(7, funcionario.getSenha());
            statement.setInt(8, funcionario.getCargo().getId());

            if (funcionario instanceof FuncionarioOperacionalBean)
                statement.setInt(9, ((FuncionarioOperacionalBean) funcionario).getDepartamentoAtuacao().getId());
            else
                statement.setNull(9, Types.NUMERIC);

            if (funcionario instanceof FuncionarioGerencialBean)
                statement.setInt(10, ((FuncionarioGerencialBean) funcionario).getDepartamentoGerenciado().getId());
            else
                statement.setNull(10, Types.NUMERIC);

            statement.executeUpdate();

            ResultSet rsId = statement.getGeneratedKeys();

            if (rsId.first()) {
                funcionario.setId(rsId.getString(1) != null ? rsId.getInt(1) : null);
            }

            if (funcionario.getId() == null) {
                throw new SQLException("Não foi identificado o ID gerado na inserção");
            }

            if (funcionario instanceof  FuncionarioExecutivoBean) {
                for (DepartamentoBean departamento : ((FuncionarioExecutivoBean) funcionario).getDepartamentos()) {
                    PreparedStatement stsDepartamentoGerenciados = connection.prepareStatement(
                            "INSERT INTO DepartamentosExecutivo ( " +
                                "  idExecutivo   , " +
                                "  idDepartamento " +
                                ") " +
                                "VALUES ( " +
                                "  ?, " +
                                "  ?  " +
                                ") "
                    );
                    stsDepartamentoGerenciados.setInt(1, funcionario.getId());
                    stsDepartamentoGerenciados.setInt(2, departamento.getId());
                    stsDepartamentoGerenciados.executeUpdate();
                }
            }

            connection.commit();
        }
        catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public void alterar(FuncionarioBean funcionarioBean) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
            "UPDATE Funcionario SET\n" +
                "  nome                     = ?,\n" +
                "  sobrenome                = ?,\n" +
                "  rg                       = ?,\n" +
                "  cpf                      = ?,\n" +
                "  telefone                 = ?,\n" +
                "  nivel                    = ?,\n" +
                "  senha                    = ?,\n" +
                "  cargoId                  = ?,\n" +
                "  idDepartamentoAtuacao    = ?,\n" +
                "  idDepartamentoGerenciado = ?\n" +
                "WHERE\n" +
                "  id = ?"
            );

            preparedStatement.setString(1, funcionarioBean.getNome());
            preparedStatement.setString(2, funcionarioBean.getSobrenome());
            preparedStatement.setString(3, funcionarioBean.getRg());
            preparedStatement.setString(4, funcionarioBean.getCpf());
            preparedStatement.setString(5, funcionarioBean.getTelefone());
            preparedStatement.setInt(6, funcionarioBean.getNivel());
            preparedStatement.setString(7, funcionarioBean.getSenha());
            preparedStatement.setInt(8, funcionarioBean.getCargo().getId());

            if (funcionarioBean instanceof FuncionarioOperacionalBean)
                preparedStatement.setInt(9, ((FuncionarioOperacionalBean) funcionarioBean).getDepartamentoAtuacao().getId());
            else
                preparedStatement.setNull(9, Types.NUMERIC);

            if (funcionarioBean instanceof FuncionarioGerencialBean)
                preparedStatement.setInt(10, ((FuncionarioGerencialBean) funcionarioBean).getDepartamentoGerenciado().getId());
            else
                preparedStatement.setNull(10, Types.NUMERIC);

            preparedStatement.setInt(11, funcionarioBean.getId());

            preparedStatement.executeUpdate();

            PreparedStatement stsExcluir = connection.prepareStatement(
                    "DELETE FROM DepartamentosExecutivo WHERE DepartamentosExecutivo.idExecutivo = ?"
            );
            stsExcluir.setInt(1, funcionarioBean.getId());
            stsExcluir.executeUpdate();

            if (funcionarioBean instanceof FuncionarioExecutivoBean) {
                for (DepartamentoBean departamento : ((FuncionarioExecutivoBean) funcionarioBean).getDepartamentos()) {
                    PreparedStatement stsDepartamentoGerenciados = connection.prepareStatement(
                    "INSERT INTO DepartamentosExecutivo ( " +
                        "  idExecutivo   , " +
                        "  idDepartamento " +
                        ") " +
                        "VALUES ( " +
                        "  ?, " +
                        "  ?  " +
                        ") "
                    );
                    stsDepartamentoGerenciados.setInt(1, funcionarioBean.getId());
                    stsDepartamentoGerenciados.setInt(2, departamento.getId());
                    stsDepartamentoGerenciados.executeUpdate();
                }
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

    public void excluir(FuncionarioBean funcionarioBean) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Funcionario WHERE id = ?"
            );

            statement.setInt(1, funcionarioBean.getId());

            statement.executeUpdate();
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public List<FuncionarioBean> listarTodos(String filtro) throws SQLException {
        return listarFuncionarios(
            (filtro == null || filtro.isEmpty() ? "" :
            "WHERE Funcionario.rg like ? " +
                "OR Funcionario.cpf like ? " +
                "OR Funcionario.nome like ? " +
                "OR Funcionario.sobrenome like ? " +
                "OR Cargo.nome like ? "),
            (statement) -> {
                if (filtro != null && !filtro.isEmpty()) {
                    try {
                        statement.setString(1, filtro + "%");
                        statement.setString(2, filtro + "%");
                        statement.setString(3, "%" + filtro + "%");
                        statement.setString(4, "%" + filtro + "%");
                        statement.setString(5, "%" + filtro + "%");
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        );
    }

    private List<FuncionarioBean> listarFuncionarios(String where, Callback<PreparedStatement, Void> parametros) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            String sql = "SELECT " +
                    "  Funcionario.id            , " +
                    "  Funcionario.nome          , " +
                    "  sobrenome                 , " +
                    "  rg                        , " +
                    "  cpf                       , " +
                    "  telefone                  , " +
                    "  nivel                     , " +
                    "  senha                     , " +
                    "  cargoId                   , " +
                    "  idDepartamentoAtuacao     , " +
                    "  idDepartamentoGerenciado  , " +
                    "  tipo                      , " +
                    "  Cargo.nome nomeCargo      , " +
                    "  salarioNivel1             , " +
                    "  salarioNivel2             , " +
                    "  salarioNivel3             , " +
                    "  percentualSalarioBonus    , " +
                    "  bonificacaoPorDepartamento, " +
                    "  bonificacaoPorPessoa      , " +
                    "  acessaSistema             , " +
                    "  dg.nome nomeDepartamentoGerenciado, " +
                    "  da.nome nomeDepartamentoAtuacao " +
                    "FROM " +
                    "  Funcionario " +
                    "INNER JOIN Cargo ON ( Cargo.id = Funcionario.cargoId ) " +
                    "LEFT JOIN Departamento dg ON ( Funcionario.idDepartamentoGerenciado = dg.id ) " +
                    "LEFT JOIN Departamento da ON ( Funcionario.idDepartamentoAtuacao = da.id ) " + where;

            PreparedStatement statement = connection.prepareStatement(sql);

            parametros.call(statement);

            ResultSet rs = statement.executeQuery();

            List<FuncionarioBean> funcionarios = new ArrayList<>();

            while (rs.next()) {
                int tipo = rs.getInt("tipo");

                // Variável declarando que funcionário pode receber um funcionário com qualquer cargo
                FuncionarioBean<?> funcionario;
                CargoBean cargo;

                // Operacional
                if (tipo == 1) {
                    CargoOperacionalBean cargoOperacional = new CargoOperacionalBean();
                    FuncionarioOperacionalBean funcionarioOperacional = new FuncionarioOperacionalBean(cargoOperacional);
                    funcionario = funcionarioOperacional;
                    cargo = cargoOperacional;

                    cargoOperacional.setAcessaSistema(rs.getBoolean("acessaSistema"));

                    if (rs.getString("idDepartamentoAtuacao") != null) {
                        DepartamentoBean departamentoAtuacao = new DepartamentoBean(
                                rs.getInt("idDepartamentoAtuacao"),
                                rs.getString("nomeDepartamentoAtuacao"));
                        funcionarioOperacional.setDepartamentoAtuacao(departamentoAtuacao);
                    }
                }
                // Gerencial
                else if (tipo == 2) {
                    CargoGerencialBean cargoGerencial = new CargoGerencialBean();
                    FuncionarioGerencialBean funcionarioGerencial = new FuncionarioGerencialBean(cargoGerencial);
                    funcionario = funcionarioGerencial;
                    cargo = cargoGerencial;

                    cargoGerencial.setBonificacaoPorPessoa(rs.getBigDecimal("bonificacaoPorPessoa"));

                    if (rs.getString("idDepartamentoGerenciado") != null) {
                        DepartamentoBean departamentoGerenciado = new DepartamentoBean(
                                rs.getInt("idDepartamentoGerenciado"),
                                rs.getString("nomeDepartamentoGerenciado")
                        );
                        funcionarioGerencial.setDepartamentoGerenciado(departamentoGerenciado);
                    }
                }
                // Executivo
                else {
                    CargoExecutivoBean cargoExecutivo = new CargoExecutivoBean();
                    FuncionarioExecutivoBean funcionarioExecutivo = new FuncionarioExecutivoBean(cargoExecutivo);
                    funcionario = funcionarioExecutivo;
                    cargo = cargoExecutivo;

                    cargoExecutivo.setBonificacaoPorDepartamento(rs.getBigDecimal("bonificacaoPorDepartamento"));
                }

                //"  id                        , " +
                //"  nome                      , " +
                //"  sobrenome                 , " +
                //"  rg                        , " +
                //"  cpf                       , " +
                //"  telefone                  , " +
                //"  nivel                     , " +
                //"  senha                     , " +

                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setRg(rs.getString("rg"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setNivel(rs.getInt("nivel"));
                funcionario.setSenha(rs.getString("senha"));

                // "  tipo                      , " +
                // "  nome nomeCargo            , " +
                // "  salarioNivel1             , " +
                // "  salarioNivel2             , " +
                // "  salarioNivel3             , " +
                // "  percentualSalarioBonus    , " +

                cargo.setId(rs.getInt("cargoId"));
                cargo.setNome(rs.getString("nomeCargo"));
                cargo.setSalarioNivel1(rs.getBigDecimal("salarioNivel1"));
                cargo.setSalarioNivel2(rs.getBigDecimal("salarioNivel2"));
                cargo.setSalarioNivel3(rs.getBigDecimal("salarioNivel3"));
                cargo.setPercentualSalarioBonus(rs.getBigDecimal("percentualSalarioBonus"));

                funcionarios.add(funcionario);
            }
            return funcionarios;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public List<DepartamentoBean> buscarDepartamentosGerenciados(FuncionarioBean<?> funcionario) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
            "SELECT\n" +
                "  idDepartamento,\n" +
                "  Departamento.nome\n" +
                "FROM\n" +
                "  DepartamentosExecutivo\n" +
                "  INNER JOIN Departamento ON (\n" +
                "    idDepartamento = Departamento.id\n" +
                "  )\n" +
                "WHERE\n" +
                "  idExecutivo = ?"
            );
            statement.setInt(1, funcionario.getId());

            List<DepartamentoBean> departamentos = new ArrayList<>();

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                departamentos.add(new DepartamentoBean(
                    rs.getInt("idDepartamento"),
                    rs.getString("nome")
                ));
            }

            return departamentos;
        }
        finally {
            if (connection != null)
                connection.close();
        }
    }

    public FuncionarioBean buscarPorCPF(String cpf) throws SQLException {
        List<FuncionarioBean> funcionarios = listarFuncionarios(" WHERE Funcionario.cpf = ? LIMIT 1", (statement) -> {
            try {
                statement.setString(1, cpf);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        if (funcionarios == null || funcionarios.isEmpty()) {
            return null;
        }

        FuncionarioBean funcionario = funcionarios.get(0);

        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT Sistema.id, Sistema.nome FROM AcessosFuncionario " +
                            "INNER JOIN Sistema ON (Sistema.id = AcessosFuncionario.idSistema) " +
                            "WHERE AcessosFuncionario.idFuncionario = ?"
            );
            statement.setInt(1, funcionario.getId());
            ResultSet rs = statement.executeQuery();

            funcionario.setSistemas(new ArrayList<>());

            while (rs.next()) {
                funcionario.getSistemas().add(new SistemaBean(rs.getInt("id"), rs.getString("nome")));
            }
        }
        finally {
            if (connection != null)
                connection.close();
        }

        return funcionario;
    }

    public List<FuncionarioBean<?>> consultarFuncionariosRelatorio() throws SQLException {
        return null;
    }
}
