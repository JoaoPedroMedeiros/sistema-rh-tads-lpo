package tads.lpo.rh.bean;

import java.util.Objects;

public class BaseBean {

    private Integer id;

    public BaseBean() {
    }

    public BaseBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() && Objects.equals(((BaseBean) obj).getId(), getId());
    }
}
