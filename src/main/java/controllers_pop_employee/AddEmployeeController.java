package controllers_pop_employee;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.LoginTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> positionView;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField tokenField;
    @FXML
    private TextField groupField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private Label wrongLabel;

    public static boolean isRefreshed;
    UsersTable addEmployee = new UsersTable();
    LoginTable loginTable = new LoginTable();
    private ObservableList<String> positions;

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
            positionList();
            isRefreshed = true;

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
        String token = tokenField.getText();
        String group = groupField.getText();

        try {
            ValidateData.goodName(name);
            ValidateData.goodSurname(surname);
            ValidateData.goodAddress(address);
            ValidateData.goodZipCode(zipCode);
            ValidateData.goodPlace(place);
            ValidateData.goodPhoneNumber(number);
            ValidateData.goodToken(token);
            ValidateData.goodGroup(group);

            addEmployee.setSurname(surname);
            addEmployee.setAddress(address);
            addEmployee.setZip(zipCode);
            addEmployee.setPlace(place);
            addEmployee.setPhoneNumber(Integer.parseInt(number));
            addEmployee.setPositionId(2);
            addEmployee.setToken(token);
            addEmployee.setGroups(Integer.parseInt(group));

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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }

    //wyświetlanie pozycji
    public void positionList() {
        try {
            DatabaseConnector.connect();
            positions = FXCollections.observableArrayList();
            positions.add("Wybierz stanowisko");
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM postions");
            while (rs.next()) {
                positions.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        positionView.setItems(positions);
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
        return isRefreshed;
    }

}
