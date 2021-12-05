package com.example.pirmasnamudarbas.entities;

import com.example.pirmasnamudarbas.enums.Goal;

import java.time.LocalDate;

public class Order {
    private Goal goal;
    private User customer;
    private LocalDate dateCreated;
    private LocalDate startDate;
    private int orderID;
    private String wishes;

    public Order(Goal goal, User customer, LocalDate dateCreated, LocalDate startDate, int orderID, String wishes) {
        this.goal = goal;
        this.customer = customer;
        this.dateCreated = dateCreated;
        this.startDate = startDate;
        this.orderID = orderID;
        this.wishes = wishes;
    }

    public Order(Goal goal, User customer, LocalDate dateCreated, LocalDate startDate, int orderID) {
        this.goal = goal;
        this.customer = customer;
        this.dateCreated = dateCreated;
        this.startDate = startDate;
        this.orderID = orderID;
    }

    public String getWishes() {
        return wishes;
    }

    public Goal getGoal() {
        return goal;
    }

    public User getCustomer() {
        return customer;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getOrderID() {
        return orderID;
    }
}
