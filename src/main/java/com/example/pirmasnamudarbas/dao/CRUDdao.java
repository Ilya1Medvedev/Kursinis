package com.example.pirmasnamudarbas.dao;

import java.util.List;

public interface CRUDdao<EntityType> {

    public void create(EntityType entity);

    public EntityType getById(int id);

    public List<EntityType> getAll();

    public void update(EntityType entity);

    public void delete(EntityType entity);
}
