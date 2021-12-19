package com.example.pirmasnamudarbas;

import com.example.pirmasnamudarbas.dao.user.UserDao;
import com.example.pirmasnamudarbas.dao.userRole.UserRoleDao;
import com.example.pirmasnamudarbas.entities.User;
import com.example.pirmasnamudarbas.entities.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PirmasNamuDarbasApplicationTests {

    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleDao userRoleDao;

    @Test
    void contextLoads() {
        UserRole userRole = new UserRole("Customer");
        userRoleDao.create(userRole);
        userDao.create(new User("John", "Wick", "1@a.ru", "password", userRole));
        System.out.println("User created!");

        userRole = userRoleDao.getByName("Customer");
        if (userDao.getById(1).getUserRole().getRole().equals(userRole.getRole())) {
            System.out.println(userRole);
            System.out.println(userRole.getId());
            System.out.println(userRole.getRole());
            System.out.println("TRUE!");
        }
    }

}
