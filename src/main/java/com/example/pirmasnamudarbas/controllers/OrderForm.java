package com.example.pirmasnamudarbas.controllers;

import com.example.pirmasnamudarbas.HelloApplication;
import com.example.pirmasnamudarbas.entities.*;
import com.example.pirmasnamudarbas.enums.Goal;
import com.example.pirmasnamudarbas.utils.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderForm {
    @FXML
    public RadioButton pumpUpRB;
    @FXML
    public RadioButton loseWeightRB;
    @FXML
    public RadioButton keepHealthRB;
    @FXML
    public RadioButton rehabilitationRB;
    @FXML
    public DatePicker startDate;
    @FXML
    public TextArea wishes;
    @FXML
    public Button cancelButton;
    @FXML
    public Button submitButton;
    @FXML
    public ToggleGroup orderType;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ManagingSystem managingSystem;
    private Customer user;
    private Goal orderTypeName;
    private List<Order> listOfOrders;
    private List<SportProgram> listOfPrograms;

    public void setData(ManagingSystem managingSystem, Customer user, List<Order> listOfOrders, List<SportProgram> listOfPrograms) {
        this.managingSystem = managingSystem;
        this.user = user;
        this.listOfOrders = listOfOrders;
        this.listOfPrograms = listOfPrograms;
    }

    public void goToProfile(ActionEvent actionEvent) throws IOException {
        returnToPrevious();
    }

    public void createOrder(ActionEvent actionEvent) throws IOException, SQLException {
        if (pumpUpRB.isSelected()) {
            orderTypeName = Goal.Pump_up;
        }
        if (loseWeightRB.isSelected()) {
            orderTypeName = Goal.Lose_weight;
        }
        if (keepHealthRB.isSelected()) {
            orderTypeName = Goal.Keep_health;
        }
        if (rehabilitationRB.isSelected()) {
            orderTypeName = Goal.Rehabilitation;
        }
        connection = DbOperations.connectToDb();
        statement = connection.createStatement();
        String query = "Select MAX(orderID) from `order`";
        ResultSet rs = statement.executeQuery(query);
        int newOrderID = 0;
        while (rs.next()) {
            newOrderID = rs.getInt("MAX(orderID)") + 1;
        }
        Order order = new Order(orderTypeName, user, LocalDate.now(), startDate.getValue(), newOrderID, wishes.getText());
        DbOperations.disconnectFromDb(connection, statement);
        user.getOrders().add(order);
        listOfOrders.add(order);
        insertOrderToDb(order);
        returnToPrevious();
    }

    private void insertOrderToDb(Order order) throws SQLException {
        connection = DbOperations.connectToDb();
        String insertString = "INSERT INTO `order`(`goal`, `dateCreated`, `startDate`, `userID`,`wishes`) VALUES (?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setString(1, String.valueOf(order.getGoal()).replace("_", " "));
        preparedStatement.setString(2, String.valueOf(order.getDateCreated()));
        preparedStatement.setString(3, String.valueOf(order.getStartDate()));
        preparedStatement.setString(4, String.valueOf(order.getCustomer().getUserID()));
        preparedStatement.setString(5, order.getWishes());
        preparedStatement.execute();
        DbOperations.disconnectFromDb(connection, preparedStatement);
    }

    public void returnToPrevious() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customer-window.fxml"));
        Parent root = fxmlLoader.load();
        CustomerWindow customerWindow = fxmlLoader.getController();
        customerWindow.setData(managingSystem, user, listOfOrders, listOfPrograms);
        Scene scene = new Scene(root);

        Stage stage = (Stage) startDate.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
