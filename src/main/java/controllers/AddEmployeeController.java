package controllers;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
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
    private TextField positionField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField tokenField;
    @FXML
    private TextField zipCodeField;




    UsersTable addEmplyee = new UsersTable();

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
                addEmplyee.setName(nameField.getText());
                addEmplyee.setSurname(surnameField.getText());
                addEmplyee.setAddress(addressField.getText());
                addEmplyee.setZip(zipCodeField.getText());
                addEmplyee.setPlace(placeField.getText());
                addEmplyee.setPhoneNumber(Integer.parseInt(numberField.getText()));
                addEmplyee.setPositionId(2);
                addEmplyee.setGroups(Integer.parseInt(numberField.getText()));
//              addEmplyee.setToken(tokenField.getText());

                //Utwórzenie połączenia z bazą danych
                DatabaseConnector.connect();

                //Utwórz zapytanie SQL do wstawienia nowego rekordu
                QExecutor.executeQuery("insert into users (name, surname, address, zip, place, phone_num, position_id, groups) values ('"
                        + addEmplyee.getName() + "','"
                        + addEmplyee.getSurname() + "','"
                        + addEmplyee.getAddress() + "','"
                        + addEmplyee.getZip() + "','"
                        + addEmplyee.getPlace() + "','"
                        + addEmplyee.getPhoneNumber() + "','"
                        + addEmplyee.getPositionId() + "','"
                        + addEmplyee.getGroups() + "')");
//                        + addEmplyee.getToken() + "')");
                        }
    }
    void addPerson() {
        nameField.getText();
        surnameField.getText();
        addressField.getText();
        zipCodeField.getText();
        placeField.getText();
        numberField.getText();
        positionField.getText(); // prawdopodobnie poprawić bo to z listy się ma wybierać i getText() może poprawnie nie pobierać nazwy
        tokenField.getText();
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

}
