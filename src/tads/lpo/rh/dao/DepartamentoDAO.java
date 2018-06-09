package tads.lpo.rh.dao;

import tads.lpo.rh.bean.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DepartamentoDAO {

    public static final DepartamentoBean DEPARTAMENTO1 = new DepartamentoBean(1, "Departamento 1");
    public static final DepartamentoBean DEPARTAMENTO2 = new DepartamentoBean(2, "Departamento 2");

    private final static List<DepartamentoBean> departamentos =
            new ArrayList<DepartamentoBean>(Arrays.asList(DEPARTAMENTO1, DEPARTAMENTO2));

    private static int i = 2;

    public void cadastrar(DepartamentoBean funcionario) {
        funcionario.setId(++this.i);
        departamentos.add(funcionario);
    }

    public void atualizar(DepartamentoBean funcionarioBean) {
        int i = -1;
        for (DepartamentoBean f: departamentos) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = departamentos.indexOf(f);
        }
        departamentos.remove(i);
        departamentos.add(i, funcionarioBean);
    }

    public void excluir(DepartamentoBean funcionarioBean) {
        int i = -1;
        for (DepartamentoBean f: departamentos) {
            if (f.getId().equals(funcionarioBean.getId()))
                i = departamentos.indexOf(f);
        }
        if (i > -1)
            departamentos.remove(i);
    }

    public List<DepartamentoBean> listarTodos(String filtro) {
        List<DepartamentoBean> departamentoBeans = filtro != null && !filtro.isEmpty() ? departamentos.stream().filter(f -> f.getNome().contains(filtro)).collect(Collectors.toList()) : departamentos;
        return departamentoBeans;
    }
}
