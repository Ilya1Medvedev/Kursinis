package com.example.pirmasnamudarbas.controllers;

import com.example.pirmasnamudarbas.HelloApplication;
import com.example.pirmasnamudarbas.entities.ManagingSystem;
import com.example.pirmasnamudarbas.entities.Order;
import com.example.pirmasnamudarbas.entities.SportProgram;
import com.example.pirmasnamudarbas.entities.Trainer;
import com.example.pirmasnamudarbas.utils.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ProgramForm {
    @FXML
    public DatePicker endDateField;
    @FXML
    public TextArea descriptionField;
    @FXML
    public Button createButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Text startDateText;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ManagingSystem managingSystem;
    private Trainer user;
    private Order order;
    private List<Order> listOfOrders;
    private List<SportProgram> listOfPrograms;
    private String customerID;
    private int orderID;


    public void setData(ManagingSystem managingSystem, Trainer user, Order order, List<Order> listOfOrders, List<SportProgram> listOfPrograms, String customerID, int orderID) {
        this.managingSystem = managingSystem;
        this.user = user;
        this.order = order;
        this.listOfOrders = listOfOrders;
        this.listOfPrograms = listOfPrograms;
        this.customerID = customerID;
        this.orderID = orderID;
        startDateText.setText(String.valueOf(order.getStartDate()));
    }

    public void createProgram(ActionEvent actionEvent) throws SQLException, IOException {
        connection = DbOperations.connectToDb();
        statement = connection.createStatement();
        String query = "Select MAX(programID) from `program`";
        ResultSet rs = statement.executeQuery(query);
        int newProgramID = 0;
        while (rs.next()) {
            newProgramID = rs.getInt("MAX(programID)") + 1;
        }
        SportProgram program = new SportProgram(false, newProgramID, order.getGoal(), LocalDate.now(), order.getStartDate(), endDateField.getValue(), descriptionField.getText(), Integer.parseInt(customerID), user.getUserID());
        insertProgramToDb(program);
        listOfPrograms.add(program);
        listOfOrders.removeIf(o -> o.getOrderID() == orderID);
        deleteOrder();
        returnTo();
    }

    private void insertProgramToDb(SportProgram program) throws SQLException {
        connection = DbOperations.connectToDb();
        String insertString = "INSERT INTO `program`(`programID`, `goal`, `dateCreated`, `startDate`, `endDate`, `userID`, `creatorID`, `description`, `completed`) VALUES (?,?,?,?,?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setInt(1, program.getProgramID());
        preparedStatement.setString(2, String.valueOf(program.getGoal()).replace("_", " "));
        preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
        preparedStatement.setDate(4, Date.valueOf(program.getStartDate()));
        preparedStatement.setDate(5, Date.valueOf(program.getEndDate()));
        preparedStatement.setInt(6, program.getUserID());
        preparedStatement.setInt(7, program.getCreatorID());
        preparedStatement.setString(8, program.getDescription());
        preparedStatement.setBoolean(9, program.isCompleted());
        preparedStatement.execute();
        DbOperations.disconnectFromDb(connection, preparedStatement);

    }

    private void deleteOrder() throws SQLException {
        connection = DbOperations.connectToDb();
        statement = connection.createStatement();
        String query = "DELETE FROM `order` WHERE orderID = '" + orderID + "'";
        statement.executeUpdate(query);
    }

    public void returnToPrevious(ActionEvent actionEvent) throws IOException {
        returnTo();
    }

    private void returnTo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trainer-window.fxml"));
        Parent root = fxmlLoader.load();
        TrainerWindow trainerWindow = fxmlLoader.getController();
        trainerWindow.setData(managingSystem, user, listOfOrders, listOfPrograms);
        Scene scene = new Scene(root);

        Stage stage = (Stage) endDateField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
