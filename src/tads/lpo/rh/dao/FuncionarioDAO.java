package tads.lpo.rh.dao;

import tads.lpo.rh.bean.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioDAO {

    public static final FuncionarioBean JOAO =
            new FuncionarioOperacionalBean(2, CargoDAO.ANALISTA, "João Pedro", "Farias", "103171137", "023.646.410-80", "41997687234", 1, "123",DepartamentoDAO.DEPARTAMENTO1);

    public static final FuncionarioBean FERNANDO =
            new FuncionarioOperacionalBean(1, CargoDAO.PROGRAMADOR, "Arion", "Viadon", "155621798", "685.102.460-84", "41997687233", 1, "123", DepartamentoDAO.DEPARTAMENTO1);

    public static final FuncionarioExecutivoBean NELSON =
            new FuncionarioExecutivoBean(3, CargoDAO.DIRETOR, "Nelson", "Vita", "339361724", "054.916.120-14", "41997687235", 1, "123",Arrays.asList(DepartamentoDAO.DEPARTAMENTO2, DepartamentoDAO.DEPARTAMENTO1));

    public static final FuncionarioGerencialBean MARCELO =
            new FuncionarioGerencialBean(4, CargoDAO.GERENTE, "Marcelo", "Rosa", "187835123", "036.179.930-60", "41997687236", 1, "123",DepartamentoDAO.DEPARTAMENTO1);


    private final static List<FuncionarioBean> funcionarios =
        new ArrayList<FuncionarioBean>(Arrays.asList(JOAO, FERNANDO, MARCELO, NELSON));

    private static int i = 4;

    public void cadastrar(FuncionarioBean funcionario) {
        funcionario.setId(++this.i);
        funcionarios.add(funcionario);
    }

    public void atualizar(FuncionarioBean funcionarioBean) {
        int i = -1;
        for (FuncionarioBean f: funcionarios) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = funcionarios.indexOf(f);
        }
        funcionarios.remove(i);
        funcionarios.add(i, funcionarioBean);
    }

    public void excluir(FuncionarioBean funcionarioBean) {
        int i = -1;
        for (FuncionarioBean f: funcionarios) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = funcionarios.indexOf(f);
        }
        if (i > -1)
            funcionarios.remove(i);
    }

    public List<FuncionarioBean> listarTodos(String filtro) {
        List<FuncionarioBean> funcionarios = filtro != null && !filtro.isEmpty() ? FuncionarioDAO.funcionarios.stream().filter(f ->
                f.getNome().contains(filtro) ||
                        f.getRg().equals(filtro) ||
                        f.getCpf().replace(".", "").replace("-", "").equals(filtro)
        ).collect(Collectors.toList()) : FuncionarioDAO.funcionarios;

        for (FuncionarioBean funcionario : funcionarios) {
            if (funcionario instanceof FuncionarioOperacionalBean) {

                DepartamentoBean dp = ((FuncionarioOperacionalBean) funcionario).getDepartamentoAtuacao();

                dp.setContagemPessoas(0);

                funcionarios.forEach(f -> {
                    if (f instanceof FuncionarioOperacionalBean &&
                            ((FuncionarioOperacionalBean) f).getDepartamentoAtuacao().equals(((FuncionarioOperacionalBean) funcionario).getDepartamentoAtuacao())) {

                        dp.setContagemPessoas(dp.getContagemPessoas() + 1);
                    }
                });
            }
        }

        return funcionarios;
    }

    public FuncionarioBean buscarPorCPF(String cpf) {
        FuncionarioBean funcionario = funcionarios.stream().filter(f -> f.getCpf().replace(".", "").replace("-", "").equals(cpf)).findFirst().orElse(null);

        if (funcionario != null) {
            for (PerfilBean perfil : new PerfilDAO().listarTodos(null)) {
                if (perfil.getFuncionario().equals(funcionario))
                    funcionario.setSistemas(perfil.getSistemas());
            }
        }

        return funcionario;
    }
}
