package com.example.pirmasnamudarbas.dao.user;

import com.example.pirmasnamudarbas.dao.CRUDdao;
import com.example.pirmasnamudarbas.entities.User;

public interface UserDao extends CRUDdao<User> {

    public User getByEmail(String email);

}
