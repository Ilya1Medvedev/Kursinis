package com.example.pirmasnamudarbas.entities;

import com.example.pirmasnamudarbas.enums.Goal;

import java.io.Serializable;
import java.time.LocalDate;

public class SportProgram implements Serializable {

    private boolean completed;
    private int programID;
    private Goal goal;
    private LocalDate dateCreated;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private int userID;
    private int creatorID;

    public SportProgram(boolean completed, int programID, Goal goal, LocalDate dateCreated, LocalDate startDate, LocalDate endDate, String description, int userID, int creatorID) {
        this.completed = completed;
        this.programID = programID;
        this.goal = goal;
        this.dateCreated = dateCreated;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.userID = userID;
        this.creatorID = creatorID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getProgramID() {
        return programID;
    }

    public Goal getGoal() {
        return goal;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public int getUserID() {
        return userID;
    }

    public int getCreatorID() {
        return creatorID;
    }
}
