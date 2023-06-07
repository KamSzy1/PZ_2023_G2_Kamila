package controllers_pop_employee;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import other.GenerateToken;
import validate.ValidateEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditEmployeeController {

    @FXML
    private GridPane addEmployeeGrid;
    @FXML
    private TextField addressField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button generateButton;
    @FXML
    private ComboBox<String> positionView;
    @FXML
    private TextField groupField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField placeField;
    @FXML
    private TextField positionField;
    @FXML
    private Button saveButton;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField tokenField;
    @FXML
    private Label wrongLabel;
    @FXML
    private TextField zipCodeField;

    private ObservableList<String> positions;
    public static boolean isRefreshed;
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
        } else if (source == saveButton) {
            updateData();
            closeWindow(saveButton);
            isRefreshed= true;
        }
    }

    public void setData(int id_user, String name, String surname, String phoneNumber, String place, String address, String group, String position, String token, String zipCode){
        nameField.setText(name);
        surnameField.setText(surname);
        numberField.setText(phoneNumber);
        placeField.setText(place);
        addressField.setText(address);
        positionView.setValue(position);
        groupField.setText(group);
        tokenField.setText(token);
        zipCodeField.setText(zipCode);
        UsersTable.setIdEditUser(id_user);
    }

    public void updateData(){
        String newName = nameField.getText();
        String newSurname = surnameField.getText();
        String newPhoneNumber = numberField.getText();
        String newPlace = placeField.getText();
        String newAddress = addressField.getText();
        int newPosition = positionView.getSelectionModel().getSelectedIndex();
        String newGroup = groupField.getText();
        String newToken = tokenField.getText();
        String newZip = zipCodeField.getText();

        try {
            ValidateEmployee.goodName(newName);
            ValidateEmployee.goodSurname(newSurname);
            ValidateEmployee.goodAddress(newAddress);
            ValidateEmployee.goodZipCode(newZip);
            ValidateEmployee.goodPlace(newPlace);
            //ValidateEmployee.goodPhoneNumber(newPhoneNumber);
            ValidateEmployee.goodToken(newToken);
            ValidateEmployee.goodGroup(newGroup);
            ValidateEmployee.goodPosition(newPosition);

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE users SET " +
                    " name = '"+ newName +  "' ," +
                    " surname = '"+ newSurname +  "' ," +
                    " phone_num = '"+ newPhoneNumber +  "' ," +
                    " place = '"+ newPlace +  "' ," +
                    " address = '"+ newAddress +  "' ," +
                    " position_id = "+ newPosition + " ," +
                    " token = '"+ newToken + "' ," +
                    " zip = '"+ newZip + "' ," +
                    " groups = "+ newGroup + " WHERE " +
                    " id_user = " + UsersTable.getIdEditUser()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void positionList() {
        try {
            DatabaseConnector.connect();
            positions = FXCollections.observableArrayList();
            positions.add("Wybierz stanowisko");
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM positions");
            while (rs.next()) {
                positions.addAll(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        positionView.setItems(positions);
    }
    //Zamykanie okienka
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
