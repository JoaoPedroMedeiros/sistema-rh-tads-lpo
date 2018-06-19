package tads.lpo.rh.gui;

import javafx.util.Callback;
import tads.lpo.rh.gui.manutencao.cargo.CadastroCargoPanel;
import tads.lpo.rh.gui.manutencao.departamento.CadastroDepartamentoPanel;
import tads.lpo.rh.gui.manutencao.funcionario.CadastroFuncionarioPanel;
import tads.lpo.rh.gui.manutencao.perfil.CadastroPerfilPanel;
import tads.lpo.rh.gui.manutencao.sistema.CadastroSistemaPanel;
import tads.lpo.rh.gui.relatorio.RelatorioFuncionarioPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MDIFrame extends JFrame {

    private Component telaAtual;

    public MDIFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(new Dimension(800,600));

        setLayout(new GridLayout(1,1));

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFuncoes = new JMenu("Funções");

        JMenu menuManutencao = new JMenu("Manutenção");
        JMenuItem menuCadastroFunc = new JMenuItem("Funcionário");
        JMenuItem menuCadastroCargo = new JMenuItem("Cargos");
        JMenuItem menuCadastroDep = new JMenuItem("Departamento");
        JMenuItem menuCadastroSist = new JMenuItem("Sistema");
        JMenuItem menuCadastroPerfil = new JMenuItem("Perfil");

        JMenu menuRelatorio = new JMenu("Relatórios");
        JMenuItem menuRelatorioFunc = new JMenuItem("Funcionário");

        menuBar.add(menuFuncoes);
        menuFuncoes.add(menuManutencao);
        menuManutencao.add(menuCadastroFunc);
        menuManutencao.add(menuCadastroCargo);
        menuManutencao.add(menuCadastroDep);
        menuManutencao.add(menuCadastroSist);
        menuManutencao.add(menuCadastroPerfil);

        menuBar.add(menuRelatorio);
        menuRelatorio.add(menuRelatorioFunc);

        super.setJMenuBar(menuBar);

        menuCadastroFunc.addActionListener(  (e) -> abrirTela(e, e1 -> cadastroFuncionario()));
        menuCadastroCargo.addActionListener( (e) -> abrirTela(e, e1 -> cadastroCargo()));
        menuCadastroDep.addActionListener(   (e) -> abrirTela(e, e1 -> cadastroDepartamento()));
        menuCadastroSist.addActionListener(  (e) -> abrirTela(e, e1 -> cadastroSistema()));
        menuCadastroPerfil.addActionListener((e) -> abrirTela(e, e1 -> cadastroPerfil()));
        menuRelatorioFunc.addActionListener( (e) -> abrirTela(e, e1 -> relatorioFuncionario()));
    }

    private void abrirTela(ActionEvent e, Callback<ActionEvent, Component> resource) {
        if (telaAtual != null) {
            remove(telaAtual);
        }

        telaAtual = resource.call(e);
        getContentPane().add(telaAtual);
        getContentPane().revalidate();
    }

    private CadastroFuncionarioPanel cadastroFuncionario() {
        CadastroFuncionarioPanel cadastroFuncionarioPanel;
        cadastroFuncionarioPanel = new CadastroFuncionarioPanel(this);
        cadastroFuncionarioPanel.setVisible(true);
        return cadastroFuncionarioPanel;
    }

    private Component cadastroDepartamento() {
        CadastroDepartamentoPanel cadastroDepartamentoPanel;
        cadastroDepartamentoPanel = new CadastroDepartamentoPanel(this);
        cadastroDepartamentoPanel.setVisible(true);
        return cadastroDepartamentoPanel;
    }

    private Component cadastroSistema() {
        CadastroSistemaPanel cadastroSistemaPanel;
        cadastroSistemaPanel = new CadastroSistemaPanel(this);
        cadastroSistemaPanel.setVisible(true);
        return cadastroSistemaPanel;
    }

    private Component cadastroCargo() {
        CadastroCargoPanel cadastroCargoPanel;
        cadastroCargoPanel = new CadastroCargoPanel(this);
        cadastroCargoPanel.setVisible(true);
        return cadastroCargoPanel;
    }

    private Component cadastroPerfil() {
        CadastroPerfilPanel cadastroPerfilPanel;
        cadastroPerfilPanel = new CadastroPerfilPanel(this);
        cadastroPerfilPanel.setVisible(true);
        return cadastroPerfilPanel;
    }

    private Component relatorioFuncionario() {
        RelatorioFuncionarioPanel relatorioFuncionarioPanel;
        relatorioFuncionarioPanel = new RelatorioFuncionarioPanel();
        relatorioFuncionarioPanel.setVisible(true);
        return relatorioFuncionarioPanel;
    }
}
