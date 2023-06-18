package controllers_pop_employee;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.PositionsTable;
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
     * Przycisk do dodawania zdań
     */
    @FXML
    private Button addButton;
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
     * Przycisk do generowania tokenu
     */
    @FXML
    private Button generateButton;
    /**
     * Przycisk do kopiowania tokenu
     */
    @FXML
    private Button copyButton;
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
     * Lista rozwijana do wyboru stanowiska pracownika
     */
    @FXML
    private ComboBox<PositionsTable> positionView;
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
     * Pole tekstowe dla numeru grupy
     */
    @FXML
    private TextField groupField;
    /**
     * Pole tekstowe dla kodu pocztowego
     */
    @FXML
    private TextField zipCodeField;
    /**
     * Tekst wyświetlający błąd
     */
    @FXML
    private Label wrongLabel;

    /**
     * Publiczna zmienna statyczna
     */
    public static boolean isRefreshed;
    /**
     * Lista do przechowywania stanowisk pracowników
     */
    private ObservableList<PositionsTable> positions;

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
            closeWindow(cancelButton);
        } else if (source == addButton) {
            addPerson();
        }
    }

    /**
     * Metoda do dodawania pracownika
     */
    private void addPerson() {
        try {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String address = addressField.getText();
            String zipCode = zipCodeField.getText();
            String place = placeField.getText();
            String number = numberField.getText();
            String token = tokenField.getText();
            String group = groupField.getText();
            PositionsTable position = positions.stream()
                    .filter(pos -> pos.getIdPosition() == positionView.getSelectionModel().getSelectedItem().getIdPosition())
                    .findFirst()
                    .get();

            ValidateEmployee.goodName(name);
            ValidateEmployee.goodSurname(surname);
            ValidateEmployee.goodAddress(address);
            ValidateEmployee.goodZipCode(zipCode);
            ValidateEmployee.goodPlace(place);
            ValidateEmployee.goodPhoneNumber(number);
            ValidateEmployee.goodToken(token);
            ValidateEmployee.goodGroup(group);

            //Utworzenie połączenia z bazą danych
            DatabaseConnector.connect();

            //Utwórz zapytanie SQL do wstawienia nowego rekordu
            QExecutor.executeQuery("INSERT INTO users (name, surname, address, zip, place, phone_num, position_id, token, groups) VALUES ('"
                    + name + "','"
                    + surname + "','"
                    + address + "','"
                    + zipCode + "','"
                    + place + "','"
                    + Integer.parseInt(number) + "','"
                    + position.getIdPosition() + "','"
                    + token + "','"
                    + Integer.parseInt(group) + "')");

            ResultSet resultSet = QExecutor.executeSelect("SELECT id_user FROM users WHERE token = '" + token + "'");
            resultSet.next();

            QExecutor.executeQuery("INSERT INTO login (user_id) VALUES (" +
                    Integer.parseInt(resultSet.getString("id_user")) + ")");
            closeWindow(addButton);
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
    private void positionList() {
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

    /**
     * Zamykanie okienka
     *
     * @param button Informacja o tym, który przycisk został kliknięty
     */
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
