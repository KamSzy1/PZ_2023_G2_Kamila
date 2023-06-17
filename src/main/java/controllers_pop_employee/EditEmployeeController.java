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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import other.GenerateToken;
import validate.ValidateEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasa obsługująca panel edytowania pracowników
 */
public class EditEmployeeController {

    /**
     * @param addButton Przycisk do dodawania zdań
     * @param addressField Pole tekstowe dla adresu
     * @param cancelButton Przycisk do zamykania okna
     * @param generateButton Przycisk do generowania tokenu
     * @param copyButton Przycisk do kopiowania tokenu
     * @param nameField Pole tekstowe dla imienia
     * @param numberField Pole tekstowe dla numeru telefonu
     * @param placeField Pole tekstowe dla miejscowości
     * @param positionView Lista rozwijana do wyboru stanowiska pracownika
     * @param surnameField Pole tekstowe dla nazwiska
     * @param tokenField Pole tekstowe dla tokenu
     * @param groupField Pole tekstowe dla numeru grupy
     * @param zipCodeField Pole tekstowe dla kodu pocztowego
     * @param wrongLabel Tekst wyświetlający błąd
     */
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
    private Button saveButton;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField tokenField;
    @FXML
    private Label wrongLabel;
    @FXML
    private TextField zipCodeField;

    /**
     * Zmienne potrzebne do działania aplikacji
     *
     * @param isRefreshed Publiczna zmienna statyczna
     * @param positions Lista do przechowywania stanowisk pracowników
     */
    public static boolean isRefreshed;
    private ObservableList<String> positions;

    /**
     * Zwraca informację, czy użytkownik został poprawnie dodany
     *
     * @return Zwraca true lub false
     */
    public static boolean refBool() {
        return isRefreshed;
    }

    /**
     * Metoda, która wykonuje się na samym początku uruchomienia się klasy. Służy do wczytania odpowiednich ustawień w panelu
     */
    @FXML
    public void initialize() {
        positionList();
    }

    /**
     * Metoda do zarządzania wszystkimi przyciskami
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException
     */
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
            closeWindow(cancelButton);
        } else if (source == saveButton) {
            updateData();
            isRefreshed = true;
        }
    }

    /**
     * Metoda do ustawiania informacji o użytkowniku
     * @param id_user Numer pracownika w bazie danych
     * @param name Imię pracownika
     * @param surname Nazwisko pracownika
     * @param phoneNumber Numer telefonu pracownika
     * @param place Miejscowość pracownika
     * @param address Adres pracownika
     * @param group Numer grupy pracownika
     * @param position Stawnowisko pracownika
     * @param token Token pracownika
     * @param zipCode Kod pocztowy pracownika
     */
    public void setData(int id_user, String name, String surname, String phoneNumber, String place, String address, String group, String position, String token, String zipCode) {
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

    /**
     * Metoda akrualizująca informacje o użytkowniku
     */
    public void updateData() {
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
            ValidateEmployee.goodPhoneNumber(newPhoneNumber);
            ValidateEmployee.goodToken(newToken);
            ValidateEmployee.goodGroup(newGroup);
            ValidateEmployee.goodPosition(newPosition);

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE users SET " +
                    " name = '" + newName + "' ," +
                    " surname = '" + newSurname + "' ," +
                    " phone_num = '" + newPhoneNumber + "' ," +
                    " place = '" + newPlace + "' ," +
                    " address = '" + newAddress + "' ," +
                    " position_id = " + newPosition + " ," +
                    " token = '" + newToken + "' ," +
                    " zip = '" + newZip + "' ," +
                    " groups = " + newGroup + " WHERE " +
                    " id_user = " + UsersTable.getIdEditUser()
            );

            closeWindow(saveButton);
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Dodawanie pozycji z bazy danych do listy
     */
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

    /**
     * Zamykanie okienka
     * @param button Informacja o tym, który przycisk został kliknięty
     */
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
