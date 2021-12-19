package com.example.pirmasnamudarbas.dao.userRole;

import com.example.pirmasnamudarbas.dao.CRUDdao;
import com.example.pirmasnamudarbas.entities.UserRole;

public interface UserRoleDao extends CRUDdao<UserRole> {

    UserRole getByName(String role);
}
