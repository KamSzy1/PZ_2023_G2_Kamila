package controllers_pop_task;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.HistoryTaskTable;
import database_classes.StatusesTable;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import other.ButtonManager;
import validate.ValidateTask;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Klasa do zarządzania dodawaniem zadań
 */
public class AddTaskController {

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
    private Button addButton;
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
     *  Zmienna do dodawania nowego zadania
     */
    private final TasksTable tasksTable = new TasksTable();
    /**
     * Zmienna do doawania czasu zadania
     */
    private final HistoryTaskTable historyTaskTable = new HistoryTaskTable();
    /**
     * Obecna data
     */
    private final LocalDate currentDate = LocalDate.now();
    /**
     * Format daty
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * Sformatowana data
     */
    private final String formattedDate = currentDate.format(formatter);
    /**
     * Lista z osobami
     */
    private ObservableList<UsersTable> names;
    /**
     * Lista z statusami
     */
    private ObservableList<StatusesTable> statuses;
    /**
     * Data
     */
    private LocalDate date;
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
        } else if (source == addButton) {
            addTask();
        }
    }

    /**
     * Dodawanie zadania do bazy danych
     */
    public void addTask() {
        try {
            tryGetData();
            DatabaseConnector.connect();
            QExecutor.executeQuery("INSERT INTO tasks (title, description, status_id, user_id) VALUES ('"
                    + tasksTable.getTitle() + "','"
                    + tasksTable.getDescription() + "','"
                    + tasksTable.getStatusId() + "','"
                    + tasksTable.getUserId() + "')");

            QExecutor.executeQuery("INSERT INTO tasks_history (planned_end, start_date, tasks_id) VALUES ('"
                    + historyTaskTable.getPlannedEnd() + "','"
                    + historyTaskTable.getStartDate() + "',"
                    + "(SELECT MAX(id_task) FROM tasks))");
            buttonManager.closeWindow(addButton);
            isRefreshed = true;
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
     * Ustawieanie danych i ich walidacja
     *
     * @throws Exception Wyrzucany wyjątek
     */
    private void tryGetData() throws Exception {
        String title = titleField.getText();
        String description = descriptionArea.getText();

        ValidateTask.checkTitle(title);
        ValidateTask.checkDescription(description);

        date = timePicker.getValue();
        tasksTable.setTitle(title);
        tasksTable.setDescription(description);
        historyTaskTable.setPlannedEnd(Date.valueOf(date));
        historyTaskTable.setStartDate(Date.valueOf(formattedDate));

        tasksTable.setStatusId(statuses.stream()
                .filter(status -> status.getIdStatus() == statusView.getSelectionModel().getSelectedItem().getIdStatus())
                .findFirst()
                .get().getIdStatus());

        tasksTable.setUserId(names.stream()
                .filter(user -> user.getIdUser() == personView.getSelectionModel().getSelectedItem().getIdUser())
                .findFirst()
                .get().getIdUser());
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