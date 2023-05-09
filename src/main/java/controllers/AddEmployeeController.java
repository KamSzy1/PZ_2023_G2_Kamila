package controllers;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.LoginTable;
import database_classes.UsersTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import other.ValidateData;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.jar.Attributes;

public class AddEmployeeController {

    @FXML
    private Button addButton;
    @FXML
    private TextField addressField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button generateButton;
    @FXML
    private Button copyButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField placeField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField tokenField;
    @FXML
    private TextField groupField;
    @FXML
    private TextField zipCodeField;

    public static boolean bool;
    UsersTable addEmployee = new UsersTable();
    LoginTable loginTable = new LoginTable();

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == generateButton) {
            //Generowanie tokena
            tokenField.setText(generateToken());

        } else if (source == copyButton) {
            //Kopiowanie tokena do schowka
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(tokenField.getText());
            clipboard.setContent(content);

        } else if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        } else if (source == addButton) {
            //Dodawanie pracownika
            addPerson();
            bool = true;

            //Zamykanie okienka
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }
    }

    //Dodawanie pracownika
    void addPerson() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String zipCode = zipCodeField.getText();
        String place = placeField.getText();
        String number = numberField.getText();

        boolean isEverythingOk = false;

        try {
            ValidateData.goodName(name);
            ValidateData.goodSurname(surname);
            ValidateData.goodAddress(address);
            ValidateData.goodZipCode(zipCode);
            ValidateData.goodPlace(place);
            ValidateData.goodPhoneNumber(number);
            isEverythingOk = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (isEverythingOk) {

            addEmployee.setSurname(surname);
            addEmployee.setAddress(address);
            addEmployee.setZip(zipCode);
            addEmployee.setPlace(place);
            addEmployee.setPhoneNumber(Integer.parseInt(number));
            addEmployee.setPositionId(2);
            addEmployee.setToken(tokenField.getText());
            addEmployee.setGroups(Integer.parseInt(groupField.getText()));

            loginTable.setToken(tokenField.getText());

            //Utwórzenie połączenia z bazą danych
            DatabaseConnector.connect();

            //Utwórz zapytanie SQL do wstawienia nowego rekordu
            QExecutor.executeQuery("INSERT INTO users (name, surname, address, zip, place, phone_num, position_id, token, groups) VALUES ('"
                    + addEmployee.getName() + "','"
                    + addEmployee.getSurname() + "','"
                    + addEmployee.getAddress() + "','"
                    + addEmployee.getZip() + "','"
                    + addEmployee.getPlace() + "','"
                    + addEmployee.getPhoneNumber() + "','"
                    + addEmployee.getPositionId() + "','"
                    + addEmployee.getToken() + "','"
                    + addEmployee.getGroups() + "')");

            QExecutor.executeQuery("INSERT INTO login (token) VALUES ('"
                    + loginTable.getToken() + "')");

        }

    }

    //Generowanie tokenu
    public static String generateToken() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwxyz1234567890";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (stringBuilder.length() < 6) {
            int i = (int) (random.nextFloat() * CHARS.length());
            stringBuilder.append(CHARS.charAt(i));
        }
        return stringBuilder.toString();
    }

    public static boolean refBool() {
        return bool;
    }

}
