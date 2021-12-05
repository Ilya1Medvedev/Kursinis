package com.example.pirmasnamudarbas.controllers;

import com.example.pirmasnamudarbas.HelloApplication;
import com.example.pirmasnamudarbas.entities.*;
import com.example.pirmasnamudarbas.enums.Goal;
import com.example.pirmasnamudarbas.enums.UserState;
import com.example.pirmasnamudarbas.utils.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button SignUpButton;
    @FXML
    public Button logInButton;

    private ManagingSystem managingSystem;
    private Connection connection;
    private Statement statement;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public void setManagingSystem(ManagingSystem managingSystem) {
        this.managingSystem = managingSystem;
    }

    public void goToSignUpForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-up-form.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
        Parent root = fxmlLoader.load();
        SignUpForm signUpForm = fxmlLoader.getController();
        signUpForm.setManagingSystem(managingSystem);

        Scene scene = new Scene(root);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Sport Programs");
        stage.setScene(scene);
        stage.show();
    }

    public void logIn(ActionEvent actionEvent) throws IOException, SQLException {

        connection = DbOperations.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE email = '" + loginField.getText() + "' AND password = '" + passwordField.getText() + "'";
        ResultSet rs = statement.executeQuery(query);
        String userName = null;
        String userSurname = null;
        String userEmail = null;
        String userPassword = null;
        String userCreatedDate = null;
        String userState = null;
        String userID = null;
        while (rs.next()) {
            userName = rs.getString("name");
            userSurname = rs.getString("surname");
            userEmail = rs.getString("email");
            userPassword = rs.getString("password");
            userCreatedDate = rs.getString("createdDate");
            userState = rs.getString("userState");
            userID = rs.getString("id");
        }
        DbOperations.disconnectFromDb(connection, statement);

        if (userName != null && !userState.equals("Trainer")) {
            Customer user = new Customer(userName, userSurname, userEmail, userPassword, LocalDate.parse(userCreatedDate, formatter), UserState.valueOf(userState), Integer.parseInt(userID));
            connection = DbOperations.connectToDb();
            statement = connection.createStatement();
            query = "Select * from `order` WHERE userID = '" + userID + "'";
            rs = statement.executeQuery(query);
            List<Order> orders = new ArrayList<>();
            String orderGoal = null;
            String orderDateCreated = null;
            String orderStartDate = null;
            int orderID = 0;
            String orderWishes = null;
            while (rs.next()) {
                orderGoal = rs.getString("goal").replace(" ", "_");
                orderDateCreated = rs.getString("dateCreated");
                orderStartDate = rs.getString("startDate");
                orderID = rs.getInt("orderID");
                orderWishes = rs.getString("wishes");
                orders.add(new Order(Goal.valueOf(orderGoal), user, LocalDate.parse(orderDateCreated, formatter), LocalDate.parse(orderStartDate, formatter), orderID, orderWishes));
            }

            List<SportProgram> programs = new ArrayList<>();
            connection = DbOperations.connectToDb();
            statement = connection.createStatement();
            query = "Select * from `program` WHERE userID = '" + userID + "'";
            rs = statement.executeQuery(query);
            int programID = 0;
            String goal = null;
            String dateCreated = null;
            String startDate = null;
            String endDate = null;
            int creatorID = 0;
            String description = null;
            boolean completed;
            while (rs.next()) {
                programID = rs.getInt("programID");
                goal = rs.getString("goal").replace(" ", "_");
                dateCreated = rs.getString("dateCreated");
                startDate = rs.getString("startDate");
                endDate = rs.getString("endDate");
                creatorID = rs.getInt("creatorID");
                description = rs.getString("description");
                completed = rs.getBoolean("completed");
                programs.add(new SportProgram(completed, programID, Goal.valueOf(goal), LocalDate.parse(dateCreated, formatter), LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter), description, Integer.parseInt(userID), creatorID));
            }


            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customer-window.fxml"));
            Parent root = fxmlLoader.load();
            CustomerWindow customerWindow = fxmlLoader.getController();
            customerWindow.setData(managingSystem, user, orders, programs);
            Scene scene = new Scene(root);

            Stage stage = (Stage) passwordField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (userName != null && userState.equals("Trainer")) {
            Trainer user = new Trainer(userName, userSurname, userEmail, userPassword, LocalDate.parse(userCreatedDate, formatter), UserState.valueOf(userState), Integer.parseInt(userID));
            connection = DbOperations.connectToDb();
            statement = connection.createStatement();
            query = "Select * from `order`";
            rs = statement.executeQuery(query);
            List<Order> orders = new ArrayList<>();
            String orderGoal = null;
            String orderDateCreated = null;
            String orderStartDate = null;
            int orderID = 0;
            String orderWishes = null;
            while (rs.next()) {
                orderGoal = rs.getString("goal").replace(" ", "_");
                orderDateCreated = rs.getString("dateCreated");
                orderStartDate = rs.getString("startDate");
                orderID = rs.getInt("orderID");
                orderWishes = rs.getString("wishes");
                orders.add(new Order(Goal.valueOf(orderGoal), user, LocalDate.parse(orderDateCreated, formatter), LocalDate.parse(orderStartDate, formatter), orderID, orderWishes));
            }
            List<SportProgram> programs = new ArrayList<>();
            connection = DbOperations.connectToDb();
            statement = connection.createStatement();
            query = "Select * from `program` WHERE creatorID = '" + userID + "'";
            rs = statement.executeQuery(query);
            int programID = 0;
            String goal = null;
            String dateCreated = null;
            String startDate = null;
            String endDate = null;
            int customerID = 0;
            String description = null;
            boolean completed;
            while (rs.next()) {
                programID = rs.getInt("programID");
                goal = rs.getString("goal").replace(" ", "_");
                dateCreated = rs.getString("dateCreated");
                startDate = rs.getString("startDate");
                endDate = rs.getString("endDate");
                customerID = rs.getInt("userID");
                description = rs.getString("description");
                completed = rs.getBoolean("completed");
                programs.add(new SportProgram(completed, programID, Goal.valueOf(goal), LocalDate.parse(dateCreated, formatter), LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter), description, customerID, Integer.parseInt(userID)));
            }

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trainer-window.fxml"));
            Parent root = fxmlLoader.load();
            TrainerWindow trainerWindow = fxmlLoader.getController();
            trainerWindow.setData(managingSystem, user, orders, programs);
            Scene scene = new Scene(root);

            Stage stage = (Stage) passwordField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            alertMessage("Invalid credentials!");
        }
    }

    private void alertMessage(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> all = new ArrayList<>();
        List<SportProgram> alls = new ArrayList<>();
        List<Order> allo = new ArrayList<>();

        managingSystem = new ManagingSystem("v1", all, alls, allo);
    }
}
