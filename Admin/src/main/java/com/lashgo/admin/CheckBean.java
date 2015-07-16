package com.lashgo.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 17.06.2015.
 */
@ManagedBean(name = "checkBean")
@SessionScoped
public class CheckBean implements Serializable {

    private List<Check> checks;

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    public void initChecks() {
        checks = HibernateUtil.getSessionFactory().openSession().createQuery("from Check order by startDate desc").list();
    }
}
