package controllers_pop_employee;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.PositionsTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import other.ButtonManager;
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
     * Pole tekstowe dla adresu
     */
    @FXML
    private TextField addressField;
    /**
     * Przycisk do zamykania okna
     */
    @FXML
    private Button cancelButton;
    /**
     * Przycisk do kopiowania tokenu
     */
    @FXML
    private Button copyButton;
    /**
     * Przycisk do generowania tokenu
     */
    @FXML
    private Button generateButton;
    /**
     * Lista rozwijana do wyboru stanowiska pracownika
     */
    @FXML
    private ComboBox<PositionsTable> positionView;
    /**
     * Pole tekstowe dla numeru grupy
     */
    @FXML
    private TextField groupField;
    /**
     * Pole tekstowe dla imienia
     */
    @FXML
    private TextField nameField;
    /**
     * Pole tekstowe dla numeru telefonu
     */
    @FXML
    private TextField numberField;
    /**
     * Pole tekstowe dla miejscowości
     */
    @FXML
    private TextField placeField;
    /**
     * Przycisk do dodawania zdań
     */
    @FXML
    private Button saveButton;
    /**
     * Pole tekstowe dla nazwiska
     */
    @FXML
    private TextField surnameField;
    /**
     * Pole tekstowe dla tokenu
     */
    @FXML
    private TextField tokenField;
    /**
     * Tekst wyświetlający błąd
     */
    @FXML
    private Label wrongLabel;
    /**
     * Pole tekstowe dla kodu pocztowego
     */
    @FXML
    private TextField zipCodeField;

    /**
     * Publiczna zmienna statyczna
     */
    public static boolean isRefreshed;
    /**
     * Lista do przechowywania stanowisk pracowników
     */
    private ObservableList<PositionsTable> positions;
    /**
     * Zarządzanie przyciskami
     */
    private ButtonManager buttonManager = new ButtonManager();

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
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException Wyrzucany wyjątek
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
            buttonManager.closeWindow(cancelButton);
        } else if (source == saveButton) {
            updateData();
        }
    }

    /**
     * Metoda do ustawiania informacji o użytkowniku
     *
     * @param id_user      Numer pracownika w bazie danych
     * @param name         Imię pracownika
     * @param surname      Nazwisko pracownika
     * @param phoneNumber  Numer telefonu pracownika
     * @param place        Miejscowość pracownika
     * @param address      Adres pracownika
     * @param group        Numer grupy pracownika
     * @param positionName Stawnowisko pracownika
     * @param token        Token pracownika
     * @param zipCode      Kod pocztowy pracownika
     */
    public void setData(int id_user, String name, String surname, String phoneNumber, String place, String address, String group, int idPosition, String positionName, String token, String zipCode) {
        nameField.setText(name);
        surnameField.setText(surname);
        numberField.setText(phoneNumber);
        placeField.setText(place);
        addressField.setText(address);

        PositionsTable positionsTable = new PositionsTable();
        positionsTable.setIdPosition(idPosition);
        positionsTable.setPositionName(positionName);
        positionView.setValue(positionsTable);

        groupField.setText(group);
        tokenField.setText(token);
        zipCodeField.setText(zipCode);
        UsersTable.setIdEditUser(id_user);
    }

    /**
     * Metoda akrualizująca informacje o użytkowniku
     */
    public void updateData() {
        try {
            String newName = nameField.getText();
            String newSurname = surnameField.getText();
            String newPhoneNumber = numberField.getText();
            String newPlace = placeField.getText();
            String newAddress = addressField.getText();
            PositionsTable newPosition = positions.stream()
                    .filter(pos -> pos.getIdPosition() == positionView.getSelectionModel().getSelectedItem().getIdPosition())
                    .findFirst()
                    .get();
            String newGroup = groupField.getText();
            String newToken = tokenField.getText();
            String newZip = zipCodeField.getText();

            ValidateEmployee.goodName(newName);
            ValidateEmployee.goodSurname(newSurname);
            ValidateEmployee.goodAddress(newAddress);
            ValidateEmployee.goodZipCode(newZip);
            ValidateEmployee.goodPlace(newPlace);
            ValidateEmployee.goodPhoneNumber(newPhoneNumber);
            ValidateEmployee.goodToken(newToken);
            ValidateEmployee.goodGroup(newGroup);

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE users SET " +
                    " name = '" + newName + "' ," +
                    " surname = '" + newSurname + "' ," +
                    " phone_num = '" + newPhoneNumber + "' ," +
                    " place = '" + newPlace + "' ," +
                    " address = '" + newAddress + "' ," +
                    " position_id = " + newPosition.getIdPosition() + " ," +
                    " token = '" + newToken + "' ," +
                    " zip = '" + newZip + "' ," +
                    " groups = " + newGroup + " WHERE " +
                    " id_user = " + UsersTable.getIdEditUser()
            );

            buttonManager.closeWindow(saveButton);
            isRefreshed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            wrongLabel.setText("Wybierz stanowisko z listy");
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }

    /**
     * Dodawanie pozycji z bazy danych do listy
     */
    public void positionList() {
        try {
            DatabaseConnector.connect();
            positions = FXCollections.observableArrayList();
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM positions");
            while (rs.next()) {
                PositionsTable positionsTable = new PositionsTable();
                positionsTable.setIdPosition(rs.getInt("id_position"));
                positionsTable.setPositionName(rs.getString("position_name"));
                positions.add(positionsTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        positionView.setItems(positions);
    }
}
