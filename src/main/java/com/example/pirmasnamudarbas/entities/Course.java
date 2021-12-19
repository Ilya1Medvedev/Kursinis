package com.example.pirmasnamudarbas.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseId")
    private int id;
    private String name;
    private String description;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "subscribers", joinColumns = {
        @JoinColumn(name = "CourseId")}, inverseJoinColumns = {
        @JoinColumn(name = "UserId")})
    private Set<User> subscribers;

    public Course() {
    }

    public Course(String name, String description, User owner) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDate.now();
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(User subscriber) {
        this.subscribers.add(subscriber);
    }

    public void deleteSubscriber(User subscriber) {
        this.subscribers.remove(subscriber);
    }
}
