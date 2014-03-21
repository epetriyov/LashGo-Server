package main.java.com.check.repository;

import com.check.model.dto.GcmRegistrationDto;
import main.java.com.check.domain.GcmRegistration;
import main.java.com.check.domain.Users;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugene on 19.03.14.
 */
@Repository
public class GcmDaoImpl implements GcmDao {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean isRegistrationIdExists(String registrationId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from GcmRegistration where registrationId = :registrationId");
        query.setParameter("registrationId", registrationId);
        List<Users> users = query.list();
        return !CollectionUtils.isEmpty(users);
    }

    @Override
    public List<String> getAllRegistrationIds() {
        Query query = sessionFactory.getCurrentSession().createQuery("from GcmRegistration");
        List<GcmRegistration> gcmRegistrations = query.list();
        List<String> registrationIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(gcmRegistrations)) {
            for (GcmRegistration gcmRegistration : gcmRegistrations) {
                registrationIds.add(gcmRegistration.getRegistrationId());
            }
        }
        return registrationIds;
    }

    @Override
    public void addRegistrationId(String uuid, GcmRegistrationDto registrationDto) {
        GcmRegistration gcmRegistration = new GcmRegistration(registrationDto.getRegistrationId(), uuid);
        sessionFactory.getCurrentSession().save(gcmRegistration);
    }
}
