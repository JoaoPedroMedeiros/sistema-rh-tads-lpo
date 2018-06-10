package tads.lpo.rh.dao;

import tads.lpo.rh.bean.PerfilBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilDAO extends BaseDAO<PerfilBean> {

    private final static List<PerfilBean> perfis =
            new ArrayList<PerfilBean>(Arrays.asList(
                new PerfilBean(1, FuncionarioDAO.JOAO, Arrays.asList(SistemaDAO.SISTEMA2)),
                new PerfilBean(2, FuncionarioDAO.FERNANDO, Arrays.asList(SistemaDAO.SISTEMA2)),
                new PerfilBean(3, FuncionarioDAO.MARCELO, Arrays.asList()),
                new PerfilBean(4, FuncionarioDAO.NELSON, Arrays.asList())
            ));

    private static int i = 4;

    public void cadastrar(PerfilBean funcionario) {
        funcionario.setId(++this.i);
        perfis.add(funcionario);
    }

    public void alterar(PerfilBean funcionarioBean) {
        int i = -1;
        for (PerfilBean f: perfis) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = perfis.indexOf(f);
        }
        perfis.remove(i);
        perfis.add(i, funcionarioBean);
    }

    public void excluir(PerfilBean funcionarioBean) {
        int i = -1;
        for (PerfilBean f: perfis) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = perfis.indexOf(f);
        }
        if (i > -1)
            perfis.remove(i);
    }

    public List<PerfilBean> listarTodos(String filtro) {
        return filtro != null && !filtro.isEmpty() ?
                perfis.stream().filter(f -> f.getFuncionario().getCpf().replace(".", "").replace("-", "").equals(filtro))
        .collect(Collectors.toList()) : perfis;
    }
}
