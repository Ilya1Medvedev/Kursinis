package com.example.pirmasnamudarbas;

import com.example.pirmasnamudarbas.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PirmasNamuDarbasApplication {

    @Autowired
    private UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(PirmasNamuDarbasApplication.class, args);
    }
}
