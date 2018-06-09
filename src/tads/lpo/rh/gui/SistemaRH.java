package tads.lpo.rh.gui;

import tads.lpo.rh.bean.*;
import tads.lpo.rh.dao.*;
import tads.lpo.rh.gui._common.FrameUtils;
import tads.lpo.rh.gui.login.Autenticavel;
import tads.lpo.rh.gui.login.LoginEvents;
import tads.lpo.rh.gui.login.LoginFrame;
import tads.lpo.rh.gui.manutencao.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SistemaRH {

    private LoginFrame frameLogin;

    private CadastroFuncionarioPanel panelCadastroFuncionario;

    private CadastroDepartamentoPanel panelCadastroDepartamento;

    private CadastroSistemaPanel panelCadastroSistema;

    private CadastroCargoPanel panelCadastroCargo;

    private CadastroPerfilPanel panelCadastroPerfil;

    private MDIFrame frameMDI;

    public void iniciar() {
        frameLogin = new LoginFrame(new LoginEvents() {

            @Override
            public List<SistemaBean> carregarSistemas() throws SQLException {
                ArrayList<SistemaBean> sistemas = new ArrayList<>();
                sistemas.add(new SistemaBean(1, "Sistema 1"));
                sistemas.add(new SistemaBean(2, "Sistema 2"));
                return sistemas;
            }

            @Override
            public boolean autenticar(SistemaBean sistema, String usuario, String senha) {
                Autenticavel autenticavel = new FuncionarioDAO().buscarPorCPF(usuario);

                if (autenticavel != null)
                    return autenticavel.autenticar(sistema, usuario, senha);

                return false;
            }

            @Override
            public void autenticou(SistemaBean sistema, String usuario) {
                iniciarSistema();

                frameLogin.setVisible(false);
                frameLogin.dispose();
            }
        });

        frameLogin.setVisible(true);
        frameLogin.centralizar();
    }

    private void iniciarSistema() {
        frameMDI = new MDIFrame(new MDIEvents() {

            @Override
            public Component cadastroFuncionario() {
                return SistemaRH.this.cadastroFuncionario();
            }

            @Override
            public Component cadastroDepartamento() {
                return SistemaRH.this.cadastroDepartamento();
            }

            @Override
            public Component cadastroSistema() {
                return SistemaRH.this.cadastroSistema();
            }

            @Override
            public Component cadastroCargo() {
                return SistemaRH.this.cadastroCargo();
            }

            @Override
            public Component cadastroPerfil() {
                return SistemaRH.this.cadastroPerfil();
            }
        });

        frameMDI.setVisible(true);
        frameMDI.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private CadastroFuncionarioPanel cadastroFuncionario() {
        panelCadastroFuncionario = new CadastroFuncionarioPanel(new CadastroFuncionarioEvents() {

            private JDialog dialog;

            @Override
            public List<DepartamentoBean> listarDepartamentos() {
                return new DepartamentoDAO().listarTodos(null);
            }

            @Override
            public List<CargoBean> listarCargos() {
                return new CargoDAO().listarTodos(null);
            }

            @Override
            public List<FuncionarioBean> listar(String filtro) {
                return new FuncionarioDAO().listarTodos(filtro);
            }

            @Override
            public void modalNovo() {
                Dimension dimension = new Dimension(600, 500);
                dialog = new JDialog(frameMDI, "Novo funcionário", true);
                dialog.getContentPane().add(new CadastroFuncionarioModal(this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }

            @Override
            public void modalEditar(FuncionarioBean funcionario) {
                Dimension dimension = new Dimension(600, 500);
                dialog = new JDialog(frameMDI, "Editar funcionário", true);
                dialog.getContentPane().add(new CadastroFuncionarioModal(funcionario, this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }

            @Override
            public void novo(FuncionarioBean funcionario) {
                new FuncionarioDAO().cadastrar(funcionario);
                dialog.dispose();

                panelCadastroFuncionario.refresh();

            }

            @Override
            public void editar(FuncionarioBean funcionario) {
                new FuncionarioDAO().atualizar(funcionario);
                dialog.dispose();

                panelCadastroFuncionario.refresh();
            }

            @Override
            public void excluir(FuncionarioBean funcionario) {
                new FuncionarioDAO().excluir(funcionario);
                panelCadastroFuncionario.refresh();
            }
        });
        panelCadastroFuncionario.setVisible(true);
        return panelCadastroFuncionario;
    }

    private Component cadastroDepartamento() {
        panelCadastroDepartamento = new CadastroDepartamentoPanel(new CadastroDepartamentoEvents() {

            private JDialog dialog;

            @Override
            public List<DepartamentoBean> listar(String filtro) {
                return new DepartamentoDAO().listarTodos(filtro);
            }

            @Override
            public void novo(DepartamentoBean target) {
                new DepartamentoDAO().cadastrar(target);
                dialog.dispose();

                panelCadastroDepartamento.refresh();
            }

            @Override
            public void editar(DepartamentoBean target) {
                new DepartamentoDAO().atualizar(target);
                dialog.dispose();

                panelCadastroDepartamento.refresh();
            }

            @Override
            public void excluir(DepartamentoBean target) {
                new DepartamentoDAO().excluir(target);
                panelCadastroDepartamento.refresh();
            }

            @Override
            public void modalNovo() {
                Dimension dimension = new Dimension(400, 140);
                dialog = new JDialog(frameMDI, "Novo departamento", true);
                dialog.getContentPane().add(new CadastroDepartamentoModal(this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }

            @Override
            public void modalEditar(DepartamentoBean target) {
                Dimension dimension = new Dimension(400, 140);
                dialog = new JDialog(frameMDI, "Alterar departamento", true);
                dialog.getContentPane().add(new CadastroDepartamentoModal(target,this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }
        });
        panelCadastroDepartamento.setVisible(true);
        return panelCadastroDepartamento;
    }

    private Component cadastroSistema() {
        panelCadastroSistema = new CadastroSistemaPanel(new CadastroSistemaEvents() {

            private JDialog dialog;

            @Override
            public List<SistemaBean> listar(String filtro) {
                return new SistemaDAO().listarTodos(filtro);
            }

            @Override
            public void novo(SistemaBean target) {
                new SistemaDAO().cadastrar(target);
                dialog.dispose();

                panelCadastroSistema.refresh();
            }

            @Override
            public void editar(SistemaBean target) {
                new SistemaDAO().atualizar(target);
                dialog.dispose();

                panelCadastroSistema.refresh();
            }

            @Override
            public void excluir(SistemaBean target) {
                new SistemaDAO().excluir(target);
                panelCadastroSistema.refresh();
            }

            @Override
            public void modalNovo() {
                Dimension dimension = new Dimension(400, 140);
                dialog = new JDialog(frameMDI, "Novo sistema", true);
                dialog.getContentPane().add(new CadastroSistemaModal(this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }

            @Override
            public void modalEditar(SistemaBean target) {
                Dimension dimension = new Dimension(400, 140);
                dialog = new JDialog(frameMDI, "Alterar sistema", true);
                dialog.getContentPane().add(new CadastroSistemaModal(target,this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }
        });
        panelCadastroSistema.setVisible(true);
        return panelCadastroSistema;
    }

    private Component cadastroCargo() {
        panelCadastroCargo = new CadastroCargoPanel(new CadastroCargoEvents() {

            private JDialog dialog;

            @Override
            public List<CargoBean> listar(String filtro) {
                return new CargoDAO().listarTodos(filtro);
            }

            @Override
            public void novo(CargoBean target) {
                new CargoDAO().cadastrar(target);
                dialog.dispose();

                panelCadastroCargo.refresh();
            }

            @Override
            public void editar(CargoBean target) {
                new CargoDAO().atualizar(target);
                dialog.dispose();

                panelCadastroCargo.refresh();
            }

            @Override
            public void excluir(CargoBean target) {
                new CargoDAO().excluir(target);
                panelCadastroCargo.refresh();
            }

            @Override
            public void modalNovo() {
                Dimension dimension = new Dimension(400, 400);
                dialog = new JDialog(frameMDI, "Novo cargo", true);
                dialog.getContentPane().add(new CadastroCargoModal(this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }

            @Override
            public void modalEditar(CargoBean target) {
                Dimension dimension = new Dimension(400, 400);
                dialog = new JDialog(frameMDI, "Alterar cargo", true);
                dialog.getContentPane().add(new CadastroCargoModal(target,this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }
        });
        panelCadastroCargo.setVisible(true);
        return panelCadastroCargo;
    }

    private Component cadastroPerfil() {
        panelCadastroPerfil = new CadastroPerfilPanel(new CadastroPerfilEvents() {

            private JDialog dialog;

            @Override
            public List<FuncionarioBean> listarFuncionarios() {
                return new FuncionarioDAO().listarTodos(null);
            }

            @Override
            public List<SistemaBean> listarSistemas() {
                return new SistemaDAO().listarTodos(null);
            }

            @Override
            public List<PerfilBean> listar(String filtro) {
                return new PerfilDAO().listarTodos(filtro);
            }

            @Override
            public void novo(PerfilBean target) {
                new PerfilDAO().cadastrar(target);
                dialog.dispose();

                panelCadastroPerfil.refresh();
            }

            @Override
            public void editar(PerfilBean target) {
                new PerfilDAO().atualizar(target);
                dialog.dispose();

                panelCadastroPerfil.refresh();
            }

            @Override
            public void excluir(PerfilBean target) {
                new PerfilDAO().excluir(target);
                panelCadastroPerfil.refresh();
            }

            @Override
            public void modalNovo() {
                Dimension dimension = new Dimension(700, 400);
                dialog = new JDialog(frameMDI, "Novo cargo", true);
                dialog.getContentPane().add(new CadastroPerfilModal(this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }

            @Override
            public void modalEditar(PerfilBean target) {
                Dimension dimension = new Dimension(700, 400);
                dialog = new JDialog(frameMDI, "Alterar cargo", true);
                dialog.getContentPane().add(new CadastroPerfilModal(target,this));

                dialog.setSize(dimension);
                dialog.setPreferredSize(dimension);

                FrameUtils.centralizar(dialog);

                dialog.setResizable(false);
                dialog.setVisible(true);
                dialog.pack();
            }
        });
        panelCadastroPerfil.setVisible(true);
        return panelCadastroPerfil;
    }
}
