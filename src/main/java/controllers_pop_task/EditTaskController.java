package controllers_pop_task;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.StatusesTable;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import other.ButtonManager;
import validate.ValidateTask;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Klasa do zarządzania edycją zadań
 */
public class EditTaskController {

    /**
     * Wybór daty
     */
    @FXML
    private DatePicker timePicker;
    /**
     * Pole tekstowe tytułu
     */
    @FXML
    private TextField titleField;
    /**
     * Lista rozwijana z osobą
     */
    @FXML
    private ComboBox<UsersTable> personView;
    /**
     * Lista rozwijana z statusem zadania
     */
    @FXML
    private ComboBox<StatusesTable> statusView;
    /**
     * Opis zadania
     */
    @FXML
    private TextArea descriptionArea;
    /**
     * Przycisk anulowania
     */
    @FXML
    private Button cancelButton;
    /**
     * Przycisk dodawania zadania
     */
    @FXML
    private Button saveButton;
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
     * Zmienna do dodawania nowego zadania
     */
    private final TasksTable task = new TasksTable();
    /**
     * Lista z osobami
     */
    private ObservableList<UsersTable> names;
    /**
     * Lista z statusami
     */
    private ObservableList<StatusesTable> statuses;
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
        userList();
        statusList();
        fieldController();
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
     * @throws IOException Wyrzucany wyjątek
     */
    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == cancelButton) {
            buttonManager.closeWindow(cancelButton);
        } else if (source == saveButton) {
            updateData();
        }
    }

    /**
     * Ustawianie informacji o zadaniu
     *
     * @param id_task     Numer zadania z bazy danych
     * @param title       Tytuł
     * @param description Opis
     * @param name        Osoba przypisana
     * @param surname     Nazwisko
     * @param status      Status
     * @param planned_end Planowana data zakończenia
     */
    public void setData(int id_task, String title, String description, int idUser, String name, String surname, int idStatus, String status, String planned_end) {
        titleField.setText(title);
        descriptionArea.setText(description);

        UsersTable usersTable = new UsersTable();
        usersTable.setIdUser(idUser);
        usersTable.setName(name);
        usersTable.setSurname(surname);
        personView.setValue(usersTable);

        StatusesTable statusesTable = new StatusesTable();
        statusesTable.setIdStatus(idStatus);
        statusesTable.setName(status);
        statusView.setValue(statusesTable);

        timePicker.setValue(LocalDate.parse(planned_end));

        TasksTable.setEditIdTask(id_task);
    }

    /**
     * Aktualizacja danych o zadaniu
     */
    public void updateData() {
        try {
            String newTitle = titleField.getText();
            String newDescription = descriptionArea.getText();

            ValidateTask.checkTitle(newTitle);
            ValidateTask.checkDescription(newDescription);

            UsersTable newName = new UsersTable();
            newName.setIdUser(names.stream()
                    .filter(user -> user.getIdUser() == personView.getSelectionModel().getSelectedItem().getIdUser())
                    .findFirst()
                    .get().getIdUser());

            StatusesTable newStatus = statuses.stream()
                    .filter(status -> status.getIdStatus() == statusView.getSelectionModel().getSelectedItem().getIdStatus())
                    .findFirst()
                    .get();
            LocalDate data = timePicker.getValue();

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE tasks SET " +
                    " title = '" + newTitle + "' ," +
                    " description = '" + newDescription + "' ," +
                    " user_id = " + newName.getIdUser() + " ," +
                    " status_id = " + newStatus.getIdStatus() + " WHERE " +
                    " id_task = " + TasksTable.getEditIdTask()
            );
            QExecutor.executeQuery("UPDATE tasks_history SET planned_end= '" + data + "' WHERE tasks_id=" + TasksTable.getEditIdTask());
            isRefreshed = true;
            buttonManager.closeWindow(saveButton);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            wrongLabel.setText("Wybierz datę zakończenia");
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }

    /**
     * Kontrola przycisków edycji zadania zależna od stanowiska użytkownika
     */
    public void fieldController() {
        try {
            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT * FROM users WHERE id_user=" + UsersTable.getIdLoginUser());
            result.next();
            if (result.getInt("position_id") == 3) {
                titleField.setDisable(true);
                descriptionArea.setDisable(true);
                personView.setDisable(true);
                timePicker.setDisable(true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            if (checkPosition.getInt("position_id") == 1) {
                rs = QExecutor.executeSelect("SELECT * FROM users");
            } else {
                rs = QExecutor.executeSelect("SELECT * FROM users WHERE groups = " + UsersTable.getGroupNumber());
            }
            names = FXCollections.observableArrayList();
            while (rs.next()) {
                UsersTable usersTable = new UsersTable();
                usersTable.setIdUser(rs.getInt("id_user"));
                usersTable.setName(rs.getString("name"));
                usersTable.setSurname(rs.getString("surname"));
                names.add(usersTable);
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
            ResultSet rs = QExecutor.executeSelect("Select * from statuses");
            while (rs.next()) {
                StatusesTable statusesTable = new StatusesTable();
                statusesTable.setIdStatus(rs.getInt("id_status"));
                statusesTable.setName(rs.getString("name"));
                statuses.add(statusesTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statusView.setItems(statuses);
    }
}