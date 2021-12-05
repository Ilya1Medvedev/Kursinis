package com.example.pirmasnamudarbas.entities;

import com.example.pirmasnamudarbas.enums.UserState;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate createdDate;
    private UserState userState;
    private int userID;

    public User(String name, String surname, String email, String password, LocalDate createdDate, UserState userState, int userID) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.userState = userState;
        this.userID = userID;
    }

    public User(String name, String surname, String email, String password, LocalDate createdDate, UserState userState) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.userState = userState;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public int getUserID() {
        return userID;
    }

    public UserState getUserState() {
        return userState;
    }
}
