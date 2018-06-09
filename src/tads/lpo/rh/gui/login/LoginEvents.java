package tads.lpo.rh.gui.login;

import tads.lpo.rh.bean.SistemaBean;

import java.sql.SQLException;
import java.util.List;

public interface LoginEvents {

    List<SistemaBean> carregarSistemas() throws SQLException;

    boolean autenticar(SistemaBean sistema, String usuario, String senha);

    void autenticou(SistemaBean sistema, String usuario);
}
