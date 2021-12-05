package com.example.pirmasnamudarbas.controllers;

import com.example.pirmasnamudarbas.HelloApplication;
import com.example.pirmasnamudarbas.entities.Customer;
import com.example.pirmasnamudarbas.entities.ManagingSystem;
import com.example.pirmasnamudarbas.enums.UserState;
import com.example.pirmasnamudarbas.utils.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignUpForm {
    @FXML
    public TextField nameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatedPswField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField emailField;
    @FXML
    public Button signUpButton;
    @FXML
    public Button cancelButton;

    private ManagingSystem managingSystem;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void setManagingSystem(ManagingSystem managingSystem) {
        this.managingSystem = managingSystem;
    }

    public void createNewUser(ActionEvent actionEvent) throws IOException, SQLException {
        Customer customer = new Customer(nameField.getText(), surnameField.getText(), emailField.getText(), passwordField.getText(), LocalDate.now(), UserState.NEW);

        connection = DbOperations.connectToDb();
        String insertString = "INSERT INTO `user`(`name`, `surname`, `email`, `password`, `createdDate`, `userState`) VALUES (?,?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getSurname());
        preparedStatement.setString(3, customer.getEmail());
        preparedStatement.setString(4, customer.getPassword());
        preparedStatement.setDate(5, Date.valueOf(customer.getCreatedDate()));
        preparedStatement.setString(6, String.valueOf(customer.getUserState()));
        preparedStatement.execute();
        DbOperations.disconnectFromDb(connection, preparedStatement);
        managingSystem.getAllSysUsers().add(customer);
        System.out.println(customer);
        returnToPrevious();
    }

    public void returnToLogInWindow(ActionEvent actionEvent) throws IOException {
        returnToPrevious();
    }

    private void returnToPrevious() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-window.fxml"));
        Parent root = fxmlLoader.load();
        LoginWindow loginWindow = fxmlLoader.getController();
        loginWindow.setManagingSystem(managingSystem);

        Scene scene = new Scene(root);

        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
