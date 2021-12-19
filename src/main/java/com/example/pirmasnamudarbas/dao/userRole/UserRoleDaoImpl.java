package com.example.pirmasnamudarbas.dao.userRole;

import com.example.pirmasnamudarbas.dao.CRUDdaoImpl;
import com.example.pirmasnamudarbas.entities.UserRole;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDaoImpl extends CRUDdaoImpl<UserRole> implements UserRoleDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserRoleDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public UserRoleDaoImpl() {
        super(UserRole.class);
    }

    @Override
    public UserRole getByName(String role) {
        try {
            return entityManager.createQuery("select u from UserRole u where u.userRole = :role", UserRole.class)
                    .setParameter("role", role)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOG.info("UserRole with role {0} not found", role);
            LOG.debug("No userRole found ", e);
            return null;
        }
    }
}
