package controllers_pop_employee;

import database.DatabaseConnector;
import database.QExecutor;
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
import other.GenerateToken;
import validate.ValidateEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private ObservableList<String> positions;

    public static boolean refBool() {
        return isRefreshed;
    }

    @FXML
    public void initialize() {
        positionList();
    }

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == generateButton) {
            //Generowanie tokena
            GenerateToken generateToken = new GenerateToken();
            String generatedToken = generateToken.generateToken();
            tokenField.setText(generatedToken);

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
            isRefreshed = true;
        }
    }

    //Dodawanie pracownika
    private void addPerson() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String zipCode = zipCodeField.getText();
        String place = placeField.getText();
        String number = numberField.getText();
        String token = tokenField.getText();
        String group = groupField.getText();
        int position = positionView.getSelectionModel().getSelectedIndex() + 1;

        try {
            ValidateEmployee.goodName(name);
            ValidateEmployee.goodSurname(surname);
            ValidateEmployee.goodAddress(address);
            ValidateEmployee.goodZipCode(zipCode);
            ValidateEmployee.goodPlace(place);
            ValidateEmployee.goodPhoneNumber(number);
            ValidateEmployee.goodToken(token);
            ValidateEmployee.goodGroup(group);
            ValidateEmployee.goodPosition(position);

            addEmployee.setName(name);
            addEmployee.setSurname(surname);
            addEmployee.setAddress(address);
            addEmployee.setZip(zipCode);
            addEmployee.setPlace(place);
            addEmployee.setPhoneNumber(Integer.parseInt(number));
            addEmployee.setPositionId(position);
            addEmployee.setToken(token);
            addEmployee.setGroups(Integer.parseInt(group));

            //Utworzenie połączenia z bazą danych
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

            ResultSet resultSet = QExecutor.executeSelect("SELECT id_user FROM users WHERE token = '" + token + "'");
            resultSet.next();

            QExecutor.executeQuery("INSERT INTO login (user_id) VALUES (" +
                    Integer.parseInt(resultSet.getString("id_user")) + ")");

            //Zamykanie okienka
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }

    //wyświetlanie pozycji
    private void positionList() {
        try {
            DatabaseConnector.connect();
            positions = FXCollections.observableArrayList();
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM positions");
            while (rs.next()) {
                positions.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        positionView.setItems(positions);
    }
}
