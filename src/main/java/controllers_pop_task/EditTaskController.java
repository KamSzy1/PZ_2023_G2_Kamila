package controllers_pop_task;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Klasa do zarządzania edycją zadań
 */
public class EditTaskController {

    /**
     * @param timePicker Wybór daty
     * @param titleField Pole tekstowe tytułu
     * @param personView Lista rozwijana z osobą
     * @param statusView Lista rozwijana z statusem zadania
     * @param descriptionArea Opis zadania
     * @param cancelButton Przycisk anulowania
     * @param addButton Przycisk dodawania zadania
     * @param wrongLabel Tekst wyświetlający błąd
     */
    @FXML
    private DatePicker timePicker;
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<String> personView;
    @FXML
    private ComboBox<String> statusView;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label wrongLabel;

    /**
     * @param isRefreshed Publiczna zmienna statyczna
     * @param task Zmienna do dodawania nowego zadania
     * @param names Lista z osobami
     * @param statuses Lista z statusami
     */
    public static boolean isRefreshed;
    private final TasksTable task = new TasksTable();
    private ObservableList<String> names;
    private ObservableList<String> statuses;

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
        userList();
        statusList();
        timePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.isBefore(today));
            }
        });

        timePicker.getEditor().setDisable(true);
    }

    /**
     * Metoda do zarządzania wszystkimi przyciskami
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException
     */
    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == cancelButton) {
            closeWindow(cancelButton);

        } else if (source == saveButton) {
            updateData();
            isRefreshed= true;
        }
    }

    /**
     * Ustawianie informacji o zadaniu
     *
     * @param id_task Numer zadania z bazy danych
     * @param title Tytuł
     * @param description Opis
     * @param name Oosba przypisana
     * @param surname Nazwisko
     * @param status Status
     * @param planned_end Planowana data zakończenia
     */
    public void setData(int id_task, String title, String description, String name, String surname, String status, String planned_end){
        titleField.setText(title);
        descriptionArea.setText(description);
        personView.setValue(name + " " + surname);
        statusView.setValue(status);
        timePicker.setValue(LocalDate.parse(planned_end));

        TasksTable.setEditIdTask(id_task);
    }

    /**
     * Aktualizacja danych o zadaniu
     */
    public void updateData(){
        String newTitle = titleField.getText();
        String newDescription = descriptionArea.getText();
        int newName = personView.getSelectionModel().getSelectedIndex();
        int newStatus = statusView.getSelectionModel().getSelectedIndex();
        LocalDate data = timePicker.getValue();
        try {
            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE tasks SET " +
                    " title = '"+ newTitle +  "' ," +
                    " description = '"+ newDescription +  "' ," +
                    " user_id = "+ newName + " ," +
                    " status_id = "+ newStatus + " WHERE " +
                    " id_task = " + TasksTable.getEditIdTask()
            );
            QExecutor.executeQuery("UPDATE tasks_history SET planned_end= '"+data+"' WHERE tasks_id="+ task.getEditIdTask());
            closeWindow(saveButton);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            wrongLabel.setText("Wybierz datę zakończenia");
        } catch (RuntimeException e){
            wrongLabel.setText("Uzupełnij wszystkie pola");
        } catch(Exception e){
            wrongLabel.setText(e.getMessage());
        }
    }

    /**
     * Dodawanie praocwników z bazy danych do listy
     */
    public void userList() {
        try {
            DatabaseConnector.connect();
            ResultSet rs;
            ResultSet checkPosition = QExecutor.executeSelect("SELECT position_id FROM users WHERE id_user = " + UsersTable.getIdLoginUser());
            checkPosition.next();
            if (checkPosition.getInt("position_id") == 1){
                rs = QExecutor.executeSelect("SELECT * FROM users");
            }
            else {
                rs = QExecutor.executeSelect("SELECT * FROM users WHERE groups = " + UsersTable.getGroupNumber());
            }
            names = FXCollections.observableArrayList();
            names.add("Wybierz osobę");
            while (rs.next()) {
                names.addAll(rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        personView.setItems(names);
    }

    /**
     * Dodawanie statusów z bazy danych do listy
     */
    public void statusList() {
        try {
            DatabaseConnector.connect();
            statuses = FXCollections.observableArrayList();
            statuses.add("Wybierz status");
            ResultSet rs = QExecutor.executeSelect("Select * from statuses");
            while (rs.next()) {
                statuses.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statusView.setItems(statuses);
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
