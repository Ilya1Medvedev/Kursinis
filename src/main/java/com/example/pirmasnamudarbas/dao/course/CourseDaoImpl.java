package com.example.pirmasnamudarbas.dao.course;

import com.example.pirmasnamudarbas.dao.CRUDdaoImpl;
import com.example.pirmasnamudarbas.entities.Course;
import com.example.pirmasnamudarbas.entities.User;
import java.util.List;
import java.util.Locale.Category;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl extends CRUDdaoImpl<Course> implements CourseDao {

    private static final Logger LOG = LoggerFactory.getLogger(CourseDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public CourseDaoImpl() {
        super(Course.class);
    }

    @Override
    public Course getByName(String name) {
        try {
            return entityManager.createQuery("select c from Course c where c.name = :name", Course.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOG.info("Course with name {0} not found", name);
            LOG.debug("No course found ", e);
            return null;
        }
    }

    @Override
    public List<Course> getUserCourses(User user, Category category) {
//        List<Course> courses = new ArrayList<>();
//
//        String query = "select c from Course c join c.subscribers s where s.id = :userId";
//        if (!category.getName().equals("All")) {
//            query += " and c.category = :category";
//            courses.addAll(entityManager.createQuery(query, Course.class)
//                    .setParameter("userId", user.getId())
//                    .setParameter("category", category)
//                    .getResultList());
//        } else {
//            courses.addAll(entityManager.createQuery(query, Course.class)
//                    .setParameter("userId", user.getId())
//                    .getResultList());
//        }
//
//        query = "select c from Course c join c.attenders a where a.user= :user";
//        if (!category.getName().equals("All")) {
//            query += " and c.category = :category";
//            courses.addAll(entityManager.createQuery(query, Course.class)
//                    .setParameter("user", user)
//                    .setParameter("category", category)
//                    .getResultList());
//        } else {
//            courses.addAll(entityManager.createQuery(query, Course.class)
//                    .setParameter("user", user)
//                    .getResultList());
//        }
//
//        query = "select c from Course c where c.owner = :user";
//        if (!category.getName().equals("All")) {
//            query += " and c.category = :category";
//            courses.addAll(entityManager.createQuery(query, Course.class)
//                    .setParameter("user", user)
//                    .setParameter("category", category)
//                    .getResultList());
//        } else {
//            courses.addAll(entityManager.createQuery(query, Course.class)
//                    .setParameter("user", user)
//                    .getResultList());
//        }
//
//        return courses;
        return null;
    }

    @Override
    public List<Course> getByCategory(Category category) {
        return entityManager
                .createQuery("select c from Course c where c.category = :category", Course.class)
                .setParameter("category", category).getResultList();
    }
}
