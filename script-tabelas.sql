CREATE TABLE IF NOT EXISTS Cargo (
  id                          INTEGER IDENTITY PRIMARY KEY,
  tipo                        INT           NOT NULL,
  nome                        VARCHAR(300)  NOT NULL,
  salarioNivel1               DECIMAL(17,2) NOT NULL,
  salarioNivel2               DECIMAL(17,2) NOT NULL,
  salarioNivel3               DECIMAL(17,2) NOT NULL,
  percentualSalarioBonus      DECIMAL(17,2) NOT NULL,
  bonificacaoPorDepartamento  DECIMAL(17,2)     NULL,
  bonificacaoPorPessoa        DECIMAL(17,2)     NULL,
  acessaSistema               BIT               NULL
);

CREATE TABLE IF NOT EXISTS Departamento (
  id                          INTEGER IDENTITY PRIMARY KEY,
  nome                        VARCHAR(300) NOT NULL        
);

CREATE TABLE IF NOT EXISTS Sistema (
  id                          INTEGER IDENTITY PRIMARY KEY,
  nome                        VARCHAR(300) NOT NULL
);

CREATE TABLE IF NOT EXISTS Funcionario (
  id                          INTEGER IDENTITY PRIMARY KEY,
  nome                        VARCHAR(300) NOT NULL,
  sobrenome                   VARCHAR(300) NOT NULL,
  rg                          VARCHAR(300) NOT NULL,
  cpf                         VARCHAR(300) NOT NULL,
  telefone                    VARCHAR(300) NOT NULL,
  nivel                       INT          NOT NULL,
  senha                       VARCHAR(300) NOT NULL,
  cargoId                     INTEGER      NOT NULL,
  idDepartamentoAtuacao       INTEGER          NULL,
  idDepartamentoGerenciado    INTEGER          NULL,
  CONSTRAINT fkDepartamentoAtuacao    FOREIGN KEY (idDepartamentoAtuacao)    REFERENCES Departamento(id),
  CONSTRAINT fkDepartamentoGerenciado FOREIGN KEY (idDepartamentoGerenciado) REFERENCES Departamento(id) 
);

CREATE TABLE IF NOT EXISTS DepartamentosExecutivo (
  id                          INTEGER IDENTITY PRIMARY KEY,
  idExecutivo                 INTEGER NOT NULL,
  idDepartamento              INTEGER NOT NULL,
  CONSTRAINT fkExecutivo      FOREIGN KEY (idExecutivo)    REFERENCES Funcionario(id),
  CONSTRAINT fkDepartamento   FOREIGN KEY (idDepartamento) REFERENCES Departamento(id)
);

CREATE TABLE IF NOT EXISTS AcessosFuncionario (
  id                          INTEGER IDENTITY PRIMARY KEY,
  idFuncionario               INTEGER NOT NULL,
  idSistema                   INTEGER NOT NULL,
  CONSTRAINT fkFuncionario    FOREIGN KEY (idFuncionario) REFERENCES Funcionario(id),
  CONSTRAINT fkSistema        FOREIGN KEY (idSistema)     REFERENCES Sistema(id)
);
