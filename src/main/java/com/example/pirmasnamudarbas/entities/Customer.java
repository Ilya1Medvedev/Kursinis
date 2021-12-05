package com.example.pirmasnamudarbas.entities;


import com.example.pirmasnamudarbas.enums.UserState;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Serializable {

    private List<SportProgram> sportPrograms;
    private List<Order> orders;

    public Customer(String name, String surname, String email, String password, LocalDate createdDate, UserState userState, int userID) {
        super(name, surname, email, password, createdDate, userState, userID);
        this.orders = new ArrayList<>();
        this.sportPrograms = new ArrayList<>();
    }
    public Customer(String name, String surname, String email, String password, LocalDate createdDate, UserState userState) {
        super(name, surname, email, password, createdDate, userState);
        this.orders = new ArrayList<>();
        this.sportPrograms = new ArrayList<>();
    }

    public Customer(String name, String surname, String email, String password, LocalDate createdDate, UserState userState, List<SportProgram> sportPrograms, List<Order> orders, int userID) {
        super(name, surname, email, password, createdDate, userState, userID);
        this.sportPrograms = sportPrograms;
        this.orders = orders;
    }



    public List<SportProgram> getSportPrograms() {
        return sportPrograms;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
