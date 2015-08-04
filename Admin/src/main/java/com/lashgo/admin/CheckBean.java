package com.lashgo.admin;

import org.hibernate.Session;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Eugene on 17.06.2015.
 */
@ManagedBean
@ViewScoped
public class CheckBean implements Serializable {


    private LazyDataModel<Check> checksModel;
    private Check selectedCheck;

    public LazyDataModel<Check> getChecksModel() {
        return checksModel;
    }

    public void setChecksModel(LazyDataModel<Check> checksModel) {
        this.checksModel = checksModel;
    }

    public Check getSelectedCheck() {
        return selectedCheck;
    }

    public void setSelectedCheck(Check selectedCheck) {
        this.selectedCheck = selectedCheck;
    }

    @PostConstruct
    public void init() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        final int totalCount = ((Long) session.createQuery("select count(*) from  Check")
                .uniqueResult()).intValue();
        session.close();
        checksModel = new LazyDataModel<Check>() {

            private List<Check> dataset;

            @Override
            public List<Check> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                Session session = HibernateUtil.getSessionFactory().openSession();
                dataset = session.createQuery("from Check order by startDate desc").setFirstResult(first).setMaxResults(pageSize).list();
                session.close();
                setRowCount(totalCount);
                return dataset;
            }
        };

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
