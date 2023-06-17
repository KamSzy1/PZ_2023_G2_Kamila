package controllers_pop_task;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.HistoryTaskTable;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    private Button addButton;
    @FXML
    private Label wrongLabel;

    /**
     * @param isRefreshed Publiczna zmienna statyczna
     * @param tasksTable Zmienna do dodawania nowego zadania
     * @param HistoryTaskTable Zmienna do doawania czasu zadania
     * @param currentDate Obecna data
     * @param formatter Format daty
     * @param formattedDate Sformatowana data
     * @param names Lista z osobami
     * @param statuses Lista z statusami
     * @param date Data
     */
    public static boolean isRefreshed;
    private final TasksTable tasksTable = new TasksTable();
    private final HistoryTaskTable historyTaskTable = new HistoryTaskTable();
    private final LocalDate currentDate = LocalDate.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String formattedDate = currentDate.format(formatter);
    private ObservableList<String> names;
    private ObservableList<String> statuses;
    private LocalDate date;

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
        } else if (source == addButton) {
            addTask();
            isRefreshed = true;
        }
    }

    /**
     * Dodawanie zadania do bazy danych
     */
    public void addTask() {
        // Dodawanie nowych zadań
        try {
            tryGetData();
            DatabaseConnector.connect();
            QExecutor.executeQuery("insert into tasks (title, description, status_id, user_id) values ('"
                    + tasksTable.getTitle() + "','"
                    + tasksTable.getDescription() + "','"
                    + tasksTable.getStatusId() + "','"
                    + tasksTable.getUserId() + "')");

            QExecutor.executeQuery("insert into tasks_history (planned_end, start_date, tasks_id) values ('"
                    + historyTaskTable.getPlannedEnd() + "','"
                    + historyTaskTable.getStartDate() + "',"
                    + "(SELECT MAX(id_task) FROM tasks))");
            closeWindow(addButton);

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
            names.add("Wybierz osobe");
            while (rs.next()) {
                names.addAll(rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        personView.setItems(names);
    }

    /**
     * Ustawieanie danych i ich walidacja
     * @throws Exception
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
        tasksTable.setStatusId(statusView.getSelectionModel().getSelectedIndex()+1);
        tasksTable.setUserId(personView.getSelectionModel().getSelectedIndex()+1);
    }

    /**
     * Dodawanie statusów z bazy danych do listy
     */
    public void statusList() {
        try {
            DatabaseConnector.connect();
            statuses = FXCollections.observableArrayList();
            statuses.add("Wybierz status");
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM statuses");
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