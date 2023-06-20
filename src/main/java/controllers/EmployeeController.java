package controllers;

import com.example.system.StageChanger;
import controllers_pop_task.EditTaskController;
import database.DatabaseConnector;
import database.QExecutor;
import database_classes.HistoryTaskTable;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import other.ButtonManager;
import pdf_generate.PdfGenerate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Klasa służąca do zarządzania panelem pracownika
 */
public class EmployeeController {

    /**
     * Przycisk do przejścia do panelu z moimi zadanami
     */
    @FXML
    private Button myTasksButton;
    /**
     * Pole tekstowe do filtrowania moich zadań
     */
    @FXML
    private TextField filterField;
    /**
     * Przycisk do przejścia do panelu z raportami
     */
    @FXML
    private Button reportButton;
    /**
     * Przycisk do przejścia do panelu z ustawieniami
     */
    @FXML
    private Button settingsButton;
    /**
     * Przycisk do wylogowania się
     */
    @FXML
    private Button logoutButton;
    /**
     * Przycisk do edycji maila
     */
    @FXML
    private Button mailEditButton;
    /**
     * Przycisk do edycji hasła
     */
    @FXML
    private Button passwordEditButton;
    /**
     * Przycisk do ustawienia ścieżki do generowania PDF
     */
    @FXML
    private Button pdfPathButton;
    /**
     * Przycisk do generowania PDF
     */
    @FXML
    private Button pdfGenerateButton;
    /**
     * Siatka w panelu moich zadań
     */
    @FXML
    private GridPane gridMyTasks;
    /**
     * Siatka w panelu raportów
     */
    @FXML
    private GridPane gridReport;
    /**
     * Siatka w panelu ustawień
     */
    @FXML
    private GridPane gridSettings;
    /**
     * Nagłówek w odpowienidch panelach
     */
    @FXML
    private Label textLabel;
    /**
     * Tekst z nazwą użytkownika
     */
    @FXML
    private Label welcomeLabel;
    /**
     * Tekst z imieniem pracownika
     */
    @FXML
    private Label nameLabel;
    /**
     * Tekst z nazwiskiem pracownika
     */
    @FXML
    private Label surnameLabel;
    /**
     * Tekst z adresem pracownika
     */
    @FXML
    private Label addressLabel;
    /**
     * Tekst z kodem pocztowym
     */
    @FXML
    private Label zipLabel;
    /**
     * Tekst z miejscowością
     */
    @FXML
    private Label placeLabel;
    /**
     * Tekst z numerem telefonu
     */
    @FXML
    private Label phoneLabel;
    /**
     * +
     * Tekst z numerem grupy
     */
    @FXML
    private Label groupLabel;
    /**
     * Tekst wyświetlający się w przypadku błędu w panelu generowania PDF
     */
    @FXML
    private Label wrongPdfLabel;
    /**
     * Kolumna z opisem zadania w panelu moich zadań
     */
    @FXML
    private TableColumn<?, ?> myTaskDescription;
    /**
     * Kolumna z edycją zadania w panelu moich zadań
     */
    @FXML
    private TableColumn<?, ?> myTaskEdit;
    /**
     * Kolumna z planowaną datą zakończenia zadania w panelu moich zadań
     */
    @FXML
    private TableColumn<?, ?> myTaskPlannedDate;
    /**
     * Kolumna z statusem zadania w panelu moich zadań
     */
    @FXML
    private TableColumn<?, ?> myTaskStatus;
    /**
     * Kolumna z tytułem zadania w panelu moich zadań
     */
    @FXML
    private TableColumn<?, ?> myTaskTitle;
    /**
     * Tabela z moimi zadaniami w panelu moje zadania
     */
    @FXML
    private TableView<TasksTable> myTaskTableView;
    /**
     * Pole tekstowe do ścieżki w panelu generowania PDF
     */
    @FXML
    private TextField pdfPathField;
    /**
     * Lista rozwijana do wyboru statusu zadania
     */
    @FXML
    private ComboBox pdfChooseStatusComboBox;
    /**
     * Główne okno aplikacji
     */
    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * Lista z zadaniami w panelu moje zadania
     */
    private ObservableList<TasksTable> myTaskTable;
    /**
     * Zarządzanie przyciskami
     */
    private ButtonManager buttonManager = new ButtonManager();
    /**
     * Linia czasowa do odświeżania tabel
     */
    private Timeline time;
    /**
     * Publiczna zmienna statyczna
     */
    public static boolean isRefreshed;
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
    private void initialize() {
        welcomeLabel.setText("Witaj " + UsersTable.getLoginName() + " " + UsersTable.getLoginSurname() + "!");
        gridMyTasks.toFront();
        myTask();
        pdfChooseDataToGenerate();
    }

    /**
     * Metoda do zmieniania paneli w aplikacji
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     */
    @FXML
    public void buttonsHandlerPane(ActionEvent event) {
        Object source = event.getSource();

        if (source == myTasksButton) {
            gridMyTasks.toFront();
            textLabel.setText("Moje zadania");
        } else if (source == reportButton) {
            gridReport.toFront();
            textLabel.setText("Generowanie raportów");
            wrongPdfLabel.setText("");
        } else if (source == settingsButton) {
            pdfPathField.clear();
            gridSettings.toFront();
            textLabel.setText("Ustawienia");
            data();
        }
    }

    /**
     * Metoda do zarządzania wszystckih przycisków, które zmieniają całe panele oraz otwierają wyskakujące okienka
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException Wyrzucany wyjątek
     */
    @FXML
    public void buttonsHandlerStages(ActionEvent event) throws IOException {
        Object source = event.getSource();
        StageChanger stageChanger = new StageChanger();

        if (source == logoutButton) {
            stageChanger.changeSize(915, 630);
            stageChanger.changeScene("/main.fxml");
        } else if (source == mailEditButton) {
            String fxmlPath = "/pop_settings/editEmailInSettings.fxml";
            buttonManager.openWindow(mailEditButton, fxmlPath);
        } else if (source == passwordEditButton) {
            String fxmlPath = "/pop_settings/editPasswordInSettings.fxml";
            buttonManager.openWindow(passwordEditButton, fxmlPath);
        }
    }

    /**
     * Metoda do zarządzania tym co znajduje się w panelu raportów
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     */
    @FXML
    public void buttonReports(ActionEvent event) {
        Object source = event.getSource();

        if (source == pdfPathButton) {
            pdfPathField.setText(buttonManager.setPathPdfGenerator(mainAnchorPane));
        } else if (source == pdfGenerateButton) {
            if (!pdfPathField.getText().isEmpty()) {
                PdfGenerate.generateForEmployee(
                        pdfPathField.getText(),
                        pdfChooseStatusComboBox.getSelectionModel().getSelectedItem().toString(),
                        UsersTable.getIdLoginUser()
                );
                wrongPdfLabel.setText("PDF został wygenerowany");
            } else {
                wrongPdfLabel.setText("Ustaw ścieżkę zapisu PDF");
            }
        }
    }

    /**
     * Metoda do wczytywania danych o użytkowniku w panelu ustawień
     */
    private void data() {
        try {
            DatabaseConnector.connect();
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM users where id_user=" + UsersTable.getIdLoginUser());
            while (rs.next()) {
                nameLabel.setText(rs.getString("name"));
                surnameLabel.setText(rs.getString("surname"));
                addressLabel.setText(rs.getString("address"));
                zipLabel.setText(rs.getString("zip"));
                placeLabel.setText(rs.getString("place"));
                phoneLabel.setText(String.valueOf(rs.getInt("phone_num")));
                groupLabel.setText(String.valueOf(rs.getInt("groups")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do wyświetlania danych w panelu moje zadania oraz do filtrowania ich
     */
    private void myTask() {
        try {
            DatabaseConnector.connect();
            myTaskTable = FXCollections.observableArrayList();

            ResultSet result = QExecutor.executeSelect("SELECT * FROM tasks " +
                    "JOIN statuses ON tasks.status_id = statuses.id_status " +
                    "JOIN tasks_history ON tasks_history.tasks_id=tasks.id_task " +
                    "WHERE user_id = " + UsersTable.getIdLoginUser());
            while (result.next()) {

                Button editButton = new Button("Edycja");
                String idTask = result.getString("id_task");
                editButton.setOnAction(event -> {
                    preparePopUpWindowEditTask(idTask);
                });
                TasksTable task = new TasksTable();
                HistoryTaskTable htask = new HistoryTaskTable();
                task.setTitle(result.getString("title"));
                task.setData(result.getDate("planned_end"));
                task.setDescription(result.getString("description"));
                task.setNameStatus(result.getString("name"));
                task.setEditTaskButton(editButton);
                myTaskTable.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myTaskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        myTaskPlannedDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        myTaskDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        myTaskStatus.setCellValueFactory(new PropertyValueFactory<>("nameStatus"));
        myTaskEdit.setCellValueFactory(new PropertyValueFactory<>("editTaskButton"));

        myTaskTableView.setItems(myTaskTable);

        FilteredList<TasksTable> filteredTaskData = new FilteredList<>(myTaskTable, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTaskData.setPredicate(task -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (task.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (task.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (task.getNameStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<TasksTable> sortedData = new SortedList<>(filteredTaskData);
        sortedData.comparatorProperty().bind(myTaskTableView.comparatorProperty());
        myTaskTableView.setItems(sortedData);
    }

    /**
     * Przygotowanie wyskakującego okienka z edycją zadań
     *
     * @param idTask Numer zadania, które chcemy edytować
     */
    private void preparePopUpWindowEditTask(String idTask) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pop_task/editTask.fxml"));
            AnchorPane anchorPane = loader.load();
            EditTaskController editTaskController = loader.getController();

            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT t.id_task, t.title, t.description, u.id_user, u.name, u.surname, s.id_status, s.name AS status, tk.planned_end FROM tasks AS t " +
                    "JOIN statuses AS s ON t.status_id = s.id_status " +
                    "JOIN users AS u ON t.user_id=u.id_user " +
                    "JOIN tasks_history AS tk ON tk.tasks_id=t.id_task " +
                    "WHERE t.id_task = " + idTask);
            result.next();

            editTaskController.setData(
                    result.getInt("id_task"),
                    result.getString("title"),
                    result.getString("description"),
                    result.getInt("id_user"),
                    result.getString("name"),
                    result.getString("surname"),
                    result.getInt("id_status"),
                    result.getString("status"),
                    result.getString("planned_end"));
            time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (EditTaskController.refBool()) {
                        refreshEditTask();
                        time.stop();
                        EditTaskController.isRefreshed = false;
                    }
                }
            }));
            time.setCycleCount(Timeline.INDEFINITE);
            time.play();

            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ICON.png"))));
            stage.show();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Odświeżanie edycji zadań
     */
    private void refreshEditTask() {
        myTaskTable.clear();
        myTask();
    }

    /**
     * Wybranie typu generowanego PDF, jaki nas interesuje
     */
    private void pdfChooseDataToGenerate() {
        ObservableList<String> pdfData = FXCollections.observableArrayList();

        try {
            DatabaseConnector.connect();
            ResultSet rs = QExecutor.executeSelect("SELECT name FROM statuses");
            pdfData.add("Wszystko");
            while (rs.next()) {
                pdfData.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pdfChooseStatusComboBox.setItems(pdfData);
        pdfChooseStatusComboBox.setPromptText("Wybierz rodzaj");
    }
}