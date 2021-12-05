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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TrainerWindow {
    @FXML
    public Button quitButton;
    @FXML
    public Text userName;
    @FXML
    public Text email;
    @FXML
    public Text creationDate;
    @FXML
    public Text daysWorked;
    @FXML
    public Button createSPButton;
    @FXML
    public ListView orders;
    @FXML
    public ListView sportPrograms;
    @FXML
    public Text wishesArea;
    @FXML
    public TextArea descriptionArea;
    @FXML
    public Button updateButton;
    @FXML
    public Text startAndEndDates;
    @FXML
    public Text startDateText;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private Connection connection;
    private Statement statement;
    private ManagingSystem managingSystem;
    private Trainer user;
    private List<Order> listOfOrders;
    private List<SportProgram> listOfPrograms;

    public void setData(ManagingSystem managingSystem, Trainer user, List<Order> listOfOrders, List<SportProgram> listOfPrograms) {
        this.managingSystem = managingSystem;
        this.user = user;
        this.listOfOrders = listOfOrders;
        this.listOfPrograms = listOfPrograms;
        userName.setText(user.getName() + " " + user.getSurname());
        email.setText(user.getEmail());
        creationDate.setText(String.valueOf(user.getCreatedDate()));
        daysWorked.setText(String.valueOf(ChronoUnit.DAYS.between(user.getCreatedDate(), LocalDate.now())));
        wishesArea.setText("");
        startAndEndDates.setText("");
        startDateText.setText("");
        updateButton.setDisable(true);
        createSPButton.setDisable(true);
        fillTables();
    }

    public void fillTables() {
        sportPrograms.getItems().clear();
        for (SportProgram p : listOfPrograms) {
            sportPrograms.getItems().add("ID: " + p.getProgramID() + " Goal: " + p.getGoal().toString().replace("_", " "));
        }

        orders.getItems().clear();
        for (Order p : listOfOrders) {
            orders.getItems().add("ID: " + p.getOrderID() + " order date: " + p.getDateCreated() + " Goal: " + p.getGoal().toString().replace("_", " "));
        }
    }

    public void quit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-window.fxml"));
        Parent root = fxmlLoader.load();
        LoginWindow loginWindow = fxmlLoader.getController();
        loginWindow.setManagingSystem(managingSystem);

        Scene scene = new Scene(root);
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.setTitle("Sport Programs");
        stage.setScene(scene);
        stage.show();
    }

    public void goToSPForm(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("program-form.fxml"));
        Parent root = fxmlLoader.load();
        ProgramForm programForm = fxmlLoader.getController();
        String id;
        String orderInfo = orders.getSelectionModel().getSelectedItem().toString();
        List<String> hardcode = List.of(orderInfo.split("ID: "));
        List<String> hardcode1 = List.of(hardcode.get(1).split(" "));
        id = hardcode1.get(0);
        connection = DbOperations.connectToDb();
        statement = connection.createStatement();
        String query = "Select * from `order` WHERE orderID = '" + id + "'";
        ResultSet rs = statement.executeQuery(query);
        String orderGoal = null;
        String orderDateCreated = null;
        String orderStartDate = null;
        int orderID = 0;
        String orderWishes = null;
        String customerID = null;
        while (rs.next()) {
            orderGoal = rs.getString("goal").replace(" ", "_");
            orderDateCreated = rs.getString("dateCreated");
            orderStartDate = rs.getString("startDate");
            orderID = rs.getInt("orderID");
            orderWishes = rs.getString("wishes");
            customerID = rs.getString("userID");
        }
        Order order = new Order(Goal.valueOf(orderGoal), user, LocalDate.parse(orderDateCreated, formatter), LocalDate.parse(orderStartDate, formatter), orderID, orderWishes);
        DbOperations.disconnectFromDb(connection, statement);
        programForm.setData(managingSystem, user, order, listOfOrders, listOfPrograms, customerID, orderID);
        Scene scene = new Scene(root);
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.setTitle("Sport Programs");
        stage.setScene(scene);
        stage.show();
    }

    public void showDescriptionOfOrder(MouseEvent mouseEvent) throws SQLException {
        if ((orders.getSelectionModel().getSelectedItem() != null)) {
            wishesArea.setText(getDescription(orders, "wishes", "`order`", "orderID"));
            startDateText.setText("Start date: " + getDescription(orders, "startDate", "`order`", "orderID"));
            createSPButton.setDisable(false);
        }
    }

    public void showDescriptionOfProgram(MouseEvent mouseEvent) throws SQLException {
        if ((sportPrograms.getSelectionModel().getSelectedItem() != null)) {
            descriptionArea.setText(getDescription(sportPrograms, "description", "`program`", "programID"));
            String endDate = getDescription(sportPrograms, "endDate", "`program`", "programID");
            String startDate = getDescription(sportPrograms, "startDate", "`program`", "programID");
            startAndEndDates.setText("Start date: " + startDate + " End date: " + endDate);
            updateButton.setDisable(false);
        }
    }

    public String getDescription(ListView list, String columnName, String tableName, String whColumnName) throws SQLException {
        String id;
        String orderInfo = list.getSelectionModel().getSelectedItem().toString();
        List<String> hardcode = List.of(orderInfo.split("ID: "));
        List<String> hardcode1 = List.of(hardcode.get(1).split(" "));
        id = hardcode1.get(0);
        connection = DbOperations.connectToDb();
        statement = connection.createStatement();
        String query = "Select * From " + tableName + " Where " + whColumnName + " = '" + id + "'";
        ResultSet rs = statement.executeQuery(query);
        String description = null;
        while (rs.next()) {
            description = rs.getString(columnName);
        }
        DbOperations.disconnectFromDb(connection, statement);
        return description;
    }

    public void updateProgram(ActionEvent actionEvent) throws SQLException {
        String id;
        String orderInfo = sportPrograms.getSelectionModel().getSelectedItem().toString();
        List<String> hardcode = List.of(orderInfo.split("ID: "));
        List<String> hardcode1 = List.of(hardcode.get(1).split(" "));
        id = hardcode1.get(0);
        connection = DbOperations.connectToDb();
        String updateString = "UPDATE `program` SET `description`='" + descriptionArea.getText() + "' WHERE `programID` = '" + id + "'";
        statement = connection.createStatement();
        statement.executeUpdate(updateString);
        DbOperations.disconnectFromDb(connection, statement);
    }
}
