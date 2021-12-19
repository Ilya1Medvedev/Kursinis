package com.example.pirmasnamudarbas.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public abstract class CRUDdaoImpl<EntityType> implements CRUDdao<EntityType> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class clazz;

    public CRUDdaoImpl(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void create(EntityType currentEntity) {
        entityManager.persist(currentEntity);
    }

    @Override
    public EntityType getById(int id) {
        return (EntityType) entityManager.find(clazz, id);
    }

    @Override
    public List<EntityType> getAll() {
        return entityManager.createQuery("select o from " + clazz.getSimpleName() + " o").getResultList();
    }

    @Override
    public void update(EntityType currentEntity) {
        entityManager.merge(currentEntity);
    }

    @Override
    public void delete(EntityType currentEntity) {
        entityManager.remove(currentEntity);
    }
}
