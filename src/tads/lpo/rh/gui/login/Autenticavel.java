package tads.lpo.rh.gui.login;

import tads.lpo.rh.bean.SistemaBean;

public interface Autenticavel {

    boolean autenticar(SistemaBean sistema, String cpf, String senha);
}
