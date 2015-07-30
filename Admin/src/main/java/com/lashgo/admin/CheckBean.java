package com.lashgo.admin;

import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 17.06.2015.
 */
@ManagedBean
@SessionScoped
public class CheckBean implements Serializable {

    private List<Check> checks;

    public Check getSelectedCheck() {
        return selectedCheck;
    }

    public void setSelectedCheck(Check selectedCheck) {
        this.selectedCheck = selectedCheck;
    }

    private Check selectedCheck;

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    public void initChecks() {
        checks = HibernateUtil.getSessionFactory().openSession().createQuery("from Check order by startDate desc").setMaxResults(30).list();
    }

    public void deleteCheck() {
        if (selectedCheck != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.delete(selectedCheck);
            session.flush();
            session.close();
        }
    }
}
