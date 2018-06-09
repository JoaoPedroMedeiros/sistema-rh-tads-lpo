package tads.lpo.rh.dao;

import tads.lpo.rh.bean.CargoBean;
import tads.lpo.rh.bean.CargoExecutivoBean;
import tads.lpo.rh.bean.CargoGerencialBean;
import tads.lpo.rh.bean.CargoOperacionalBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CargoDAO {

    public static final CargoOperacionalBean PROGRAMADOR =
        new CargoOperacionalBean(1, "Programador", new BigDecimal("4000"),  new BigDecimal("3000"), new BigDecimal("2000"), new BigDecimal("0.8"));

    public static final CargoOperacionalBean ANALISTA =
            new CargoOperacionalBean(2, "Analista", new BigDecimal("6000"),  new BigDecimal("5000"), new BigDecimal("4000"), new BigDecimal("1"));

    public static final CargoExecutivoBean DIRETOR =
            new CargoExecutivoBean(3, "Diretor", new BigDecimal("10000"), new BigDecimal("9000"), new BigDecimal("8000"), new BigDecimal("4"));

    public static final CargoGerencialBean GERENTE =
            new CargoGerencialBean(4, "Gerente", new BigDecimal("8000"), new BigDecimal("7000"), new BigDecimal("6000"), new BigDecimal("2"));

    private final static List<CargoBean> cargos =
        new ArrayList<>(Arrays.asList(DIRETOR, GERENTE, ANALISTA, PROGRAMADOR));

    static {
        DIRETOR.setBonificacaoPorDepartamento(new BigDecimal("3000"));
        GERENTE.setBonificacaoPorPessoa(new BigDecimal("100"));
    }

    private static int i = 4;

    public void cadastrar(CargoBean cargo) {
        cargo.setId(++this.i);
        cargos.add(cargo);
    }

    public void atualizar(CargoBean cargo) {
        int i = -1;
        for (CargoBean f: cargos) {
            if (f.getId().equals(cargo.getId()))
                i = cargos.indexOf(f);
        }
        cargos.remove(i);
        cargos.add(i, cargo);
    }

    public void excluir(CargoBean cargo) {
        int i = -1;
        for (CargoBean f: cargos) {
            if (f.getId().equals(cargo.getId()))
                i = cargos.indexOf(f);
        }
        if (i > -1)
            cargos.remove(i);
    }

    public List<CargoBean> listarTodos(String filtro) {
        return filtro != null && !filtro.isEmpty() ? cargos.stream().filter(f -> f.getNome().contains(filtro)).collect(Collectors.toList()) : cargos;
    }
}
