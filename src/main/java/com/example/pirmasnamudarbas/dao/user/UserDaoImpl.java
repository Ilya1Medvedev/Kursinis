package com.example.pirmasnamudarbas.dao.user;

import com.example.pirmasnamudarbas.dao.CRUDdaoImpl;
import com.example.pirmasnamudarbas.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends CRUDdaoImpl<User> implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User getByEmail(String email) {
        try {
            return entityManager.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOG.info("User with email " + email + " not found");
            LOG.debug("No user found ", e);
            return null;
        }
    }
}
