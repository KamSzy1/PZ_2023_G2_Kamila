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

/**
 * Klasa obsługująca panel dodawania pracowników
 */
public class AddEmployeeController {

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

    /**
     * Zmienne potrzebne do działania aplikacji
     *
     * @param isRefreshed Publiczna zmienna statyczna
     * @param addEmployee Zmienna do dodawania nowego pracownika
     * @param positions Lista do przechowywania stanowisk pracowników
     */
    public static boolean isRefreshed;
    private final UsersTable addEmployee = new UsersTable();
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
        } else if (source == addButton) {
            addPerson();
            isRefreshed = true;
        }
    }


    /**
     * Metoda do dodawania pracownika
     */
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
            closeWindow(addButton);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }

    /**
     * Dodawanie pozycji z bazy danych do listy
     */
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

    /**
     * Zamykanie okienka
     * @param button Informacja o tym, który przycisk został kliknięty
     */
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
