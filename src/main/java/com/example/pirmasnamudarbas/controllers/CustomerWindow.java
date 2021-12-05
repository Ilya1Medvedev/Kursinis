package com.example.pirmasnamudarbas.controllers;

import com.example.pirmasnamudarbas.HelloApplication;
import com.example.pirmasnamudarbas.entities.*;
import com.example.pirmasnamudarbas.utils.DbOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerWindow implements Initializable {
    @FXML
    public Text userName;
    @FXML
    public Text email;
    @FXML
    public Text creationDate;
    @FXML
    public Text daysUsed;
    @FXML
    public Button quitButton;
    @FXML
    public Button orderNewProgram;
    @FXML
    public ListView mySportPrograms;
    @FXML
    public ListView orders;
    @FXML
    public Button deleteButton;
    @FXML
    public Text textOrders;

    private Connection connection;
    private Statement statement;
    private ManagingSystem managingSystem;
    private Customer user;
    private List<Order> listOfOrders;
    private List<SportProgram> listOfPrograms;

    public void setData(ManagingSystem managingSystem, Customer user, List<Order> listOfOrders, List<SportProgram> listOfPrograms) {
        this.managingSystem = managingSystem;
        this.user = user;
        this.listOfOrders = listOfOrders;
        this.listOfPrograms = listOfPrograms;
        userName.setText(user.getName() + " " + user.getSurname());
        email.setText(user.getEmail());
        creationDate.setText(String.valueOf(user.getCreatedDate()));
        daysUsed.setText(String.valueOf(ChronoUnit.DAYS.between(user.getCreatedDate(), LocalDate.now())));
        fillTables();
        textOrders.setText("");
        deleteButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void fillTables() {
        mySportPrograms.getItems().clear();
        for (SportProgram p : listOfPrograms) {
            mySportPrograms.getItems().add("ID: " + p.getProgramID() + " Goal: " + p.getGoal().toString().replace("_", " "));
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

    public void goToOrderForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("order-form.fxml"));
        Parent root = fxmlLoader.load();
        OrderForm orderForm = fxmlLoader.getController();
        orderForm.setData(managingSystem, user, listOfOrders, listOfPrograms);

        Scene scene = new Scene(root);
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.setTitle("Sport Programs");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteOrder(ActionEvent actionEvent) throws SQLException {
        String orderID;
        String orderInfo = orders.getSelectionModel().getSelectedItem().toString();
        List<String> hardcode = List.of(orderInfo.split("ID: "));
        List<String> hardcode1 = List.of(hardcode.get(1).split(" "));
        orderID = hardcode1.get(0);
        listOfOrders.removeIf(o -> o.getOrderID() == Integer.parseInt(orderID));
        if (listOfOrders.size() == 0) {
            deleteButton.setDisable(true);
        }
        connection = DbOperations.connectToDb();
        statement = connection.createStatement();
        String query = "DELETE FROM `order` WHERE orderID = '" + orderID + "'";
        statement.executeUpdate(query);

        fillTables();
    }

    public void showDescription(MouseEvent mouseEvent) throws SQLException {
        if (mySportPrograms.getSelectionModel().getSelectedItem() != null) {
            String programID;
            String orderInfo = mySportPrograms.getSelectionModel().getSelectedItem().toString();
            List<String> hardcode = List.of(orderInfo.split("ID: "));
            List<String> hardcode1 = List.of(hardcode.get(1).split(" "));
            programID = hardcode1.get(0);
            connection = DbOperations.connectToDb();
            statement = connection.createStatement();
            String query = "Select * FROM `program` WHERE programID = '" + programID + "'";
            ResultSet rs = statement.executeQuery(query);
            String description = null;
            String startDate = null;
            String endDate = null;
            while (rs.next()) {
                description = rs.getString("description");
                startDate = rs.getString("startDate");
                endDate = rs.getString("endDate");
            }
            textOrders.setText("From: " + startDate + " To: " + endDate + "\n" + description);
            DbOperations.disconnectFromDb(connection, statement);
        }
    }

    public void activateButton(MouseEvent mouseEvent) {
        if (orders.getSelectionModel().getSelectedItem() != null) {
            deleteButton.setDisable(false);
        }
    }
}
