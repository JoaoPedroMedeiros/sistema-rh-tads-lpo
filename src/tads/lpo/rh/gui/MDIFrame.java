package tads.lpo.rh.gui;

import javafx.util.Callback;
import tads.lpo.rh.gui._common.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MDIFrame extends BaseFrame {

    private MDIEvents events;

    private Component telaAtual;

    public MDIFrame(MDIEvents events) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.events = events;

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

        menuCadastroFunc.addActionListener((e) -> abrirTela(e, e1 -> events.cadastroFuncionario()));
        menuCadastroCargo.addActionListener((e) -> abrirTela(e, e1 -> events.cadastroCargo()));
        menuCadastroDep.addActionListener((e) -> abrirTela(e, e1 -> events.cadastroDepartamento()));
        menuCadastroSist.addActionListener((e) -> abrirTela(e, e1 -> events.cadastroSistema()));
        menuCadastroPerfil.addActionListener((e) -> abrirTela(e, e1 -> events.cadastroPerfil()));
    }

    private void abrirTela(ActionEvent e, Callback<ActionEvent, Component> resource) {
        if (telaAtual != null) {
            remove(telaAtual);
        }

        telaAtual = resource.call(e);
        getContentPane().add(telaAtual);
        getContentPane().revalidate();
    }
}
