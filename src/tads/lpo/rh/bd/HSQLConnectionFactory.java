package tads.lpo.rh.bd;

import tads.lpo.rh.ConnectionFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLConnectionFactory implements ConnectionFactory {

    private String usuario;

    private String senha;

    private File arquivo;

    public HSQLConnectionFactory(String usuario, String senha, File arquivo) throws SQLException {
        this.usuario = usuario;
        this.senha = senha;
        this.arquivo = arquivo;

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            initializeTables(
                    DriverManager.getConnection("jdbc:hsqldb:file:" + this.arquivo.getPath(), usuario, senha)
            );
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeTables(Connection connection) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                "CREATE TABLE IF NOT EXISTS Cargo (                         \n" +
                    "  id                          INTEGER IDENTITY PRIMARY KEY,\n" +
                    "  tipo                        INT         ,                \n" +
                    "  name                        VARCHAR(300),                \n" +
                    "  salarioNivel1               DECIMAL,                     \n" +
                    "  salarioNivel2               DECIMAL,                     \n" +
                    "  salarioNivel3               DECIMAL,                     \n" +
                    "  percentualSalarioBonus      DECIMAL,                     \n" +
                    "  bonificacaoPorDepartamento  DECIMAL,                     \n" +
                    "  bonificacaoPorPessoa        DECIMAL,                     \n" +
                    "  acessaSistema               BIT                          \n" +
                    ")                                                          \n"
            );

            statement.execute(
                "CREATE TABLE IF NOT EXISTS Departamento (                  \n" +
                    "  id                          INTEGER IDENTITY PRIMARY KEY,\n" +
                    "  nome                        VARCHAR(300) NOT NULL        \n" +
                    ")                                                          \n"
            );

            statement.execute(
                "CREATE TABLE IF NOT EXISTS Sistema (                       \n" +
                    "  id                          INTEGER IDENTITY PRIMARY KEY,\n" +
                    "  nome                        VARCHAR(300) NOT NULL        \n" +
                    ")                                                          \n"
            );

            statement.execute(
                "CREATE TABLE IF NOT EXISTS Funcionario (                                                                 \n" +
                    "  id                          INTEGER IDENTITY PRIMARY KEY,                                              \n" +
                    "  nome                        VARCHAR(300) NOT NULL,                                                     \n" +
                    "  sobrenome                   VARCHAR(300) NOT NULL,                                                     \n" +
                    "  rg                          VARCHAR(300) NOT NULL,                                                     \n" +
                    "  cpf                         VARCHAR(300) NOT NULL,                                                     \n" +
                    "  telefone                    VARCHAR(300) NOT NULL,                                                     \n" +
                    "  nivel                       INT          NOT NULL,                                                     \n" +
                    "  senha                       VARCHAR(300) NOT NULL,                                                     \n" +
                    "  cargoId                     INTEGER      NOT NULL,                                                     \n" +
                    "  idDepartamentoAtuacao       INTEGER          NULL,                                                     \n" +
                    "  idDepartamentoGerenciado    INTEGER          NULL,                                                     \n" +
                    "  CONSTRAINT fkDepartamentoAtuacao    FOREIGN KEY (idDepartamentoAtuacao)    REFERENCES Departamento(id),\n" +
                    "  CONSTRAINT fkDepartamentoGerenciado FOREIGN KEY (idDepartamentoGerenciado) REFERENCES Departamento(id) \n" +
                    ")                                                                                                        \n"
            );

            statement.execute(
                "CREATE TABLE IF NOT EXISTS DepartamentosExecutivo (                                   \n" +
                    "  id                          INTEGER IDENTITY PRIMARY KEY,                           \n" +
                    "  idExecutivo                 INTEGER NOT NULL,                                       \n" +
                    "  idDepartamento              INTEGER NOT NULL,                                       \n" +
                    "  CONSTRAINT fkExecutivo      FOREIGN KEY (idExecutivo)    REFERENCES Funcionario(id),\n" +
                    "  CONSTRAINT fkDepartamento   FOREIGN KEY (idDepartamento) REFERENCES Departamento(id)\n" +
                    ")                                                                                     \n"
            );

            statement.execute(
                "CREATE TABLE IF NOT EXISTS AcessosFuncionario (                                      \n" +
                    "  id                          INTEGER IDENTITY PRIMARY KEY,                          \n" +
                    "  idFuncionario               INTEGER NOT NULL,                                      \n" +
                    "  idSistema                   INTEGER NOT NULL,                                      \n" +
                    "  CONSTRAINT fkFuncionario    FOREIGN KEY (idFuncionario) REFERENCES Funcionario(id),\n" +
                    "  CONSTRAINT fkSistema        FOREIGN KEY (idSistema)     REFERENCES Sistema(id)     \n" +
                    ")                                                                                    \n"

            );
        }
        finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:hsqldb:file:" + this.arquivo.getPath(), usuario, senha);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
