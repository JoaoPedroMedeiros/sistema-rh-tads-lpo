package tads.lpo.rh.dao;

import tads.lpo.rh.bean.SistemaBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaDAO extends BaseDAO<SistemaBean> {

    public static final SistemaBean SISTEMA1 = new SistemaBean(1, "Sistema 1");

    public static final SistemaBean SISTEMA2 = new SistemaBean(2, "Sistema 2");

    private final static List<SistemaBean> sistemas =
            new ArrayList<>(Arrays.asList(
                    SISTEMA1,
                    SISTEMA2
            ));

    private static int i = 2;

    public void cadastrar(SistemaBean funcionario) {
        funcionario.setId(++this.i);
        sistemas.add(funcionario);
    }

    public void alterar(SistemaBean funcionarioBean) {
        int i = -1;
        for (SistemaBean f: sistemas) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = sistemas.indexOf(f);
        }
        sistemas.remove(i);
        sistemas.add(i, funcionarioBean);
    }

    public void excluir(SistemaBean funcionarioBean) {
        int i = -1;
        for (SistemaBean f: sistemas) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = sistemas.indexOf(f);
        }
        if (i > -1)
            sistemas.remove(i);
    }

    public List<SistemaBean> listarTodos(String filtro) {
        return filtro != null && !filtro.isEmpty() ? sistemas.stream().filter(f -> f.getNome().contains(filtro)).collect(Collectors.toList()) : sistemas;
    }
}
