package com.example.pirmasnamudarbas.entities;

import com.example.pirmasnamudarbas.enums.UserState;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Trainer extends User implements Serializable {
    private List<SportProgram> madeSportPrograms;

    public Trainer(String name, String surname, String email, String password, LocalDate createdDate, UserState userState, List<SportProgram> madeSportPrograms) {
        super(name, surname, email, password, createdDate, userState);
        this.madeSportPrograms = madeSportPrograms;
    }

    public Trainer(String name, String surname, String email, String password, LocalDate createdDate, UserState userState, int userID) {
        super(name, surname, email, password, createdDate, userState, userID);
        this.madeSportPrograms = new ArrayList<>();
    }

    public List<SportProgram> getMadeSportPrograms() {
        return madeSportPrograms;
    }
}
