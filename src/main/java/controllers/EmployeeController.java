package controllers;

import com.example.system.StageChanger;
import controllers_pop_task.EditTaskController;
import database.DatabaseConnector;
import database.QExecutor;
import database_classes.HistoryTaskTable;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdf_generate.PdfGenerate;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Klasa służąca do zarządzania panelem pracownika
 */
public class EmployeeController {

    /**
     * Potrzebne zmienne z Scene Buildera, aby aplikacja działała poprawnie
     *
     *     @param myTasksButton Przycisk do przejścia do panelu z moimi zadanami
     *     @param filterField Pole tekstowe do filtrowania moich zadań
     *     @param reportButton Przycisk do przejścia do panelu z raportami
     *     @param settingsButton Przycisk do przejścia do panelu z ustawieniami
     *     @param logoutButton Przycisk do wylogowania się
     *     @param mailEditButton Przycisk do edycji maila
     *     @param passwordEditButton Przycisk do edycji hasła
     *     @param pdfPathButton Przycisk do ustawienia ścieżki do generowania PDF
     *     @param pdfGenerateButton Przycisk do generowania PDF
     *     @param gridMyTasks Siatka w panelu moich zadań
     *     @param gridReport Siatka w panelu raportów
     *     @param gridSettings Siatka w panelu ustawień
     *     @param textLabel Nagłówek w odpowienidch panelach
     *     @param welcomeLabel Tekst z nazwą użytkownika
     *     @param nameLabel Tekst z imieniem pracownika
     *     @param surnameLabel Tekst z nazwiskiem pracownika
     *     @param addressLabel Tekst z adresem pracownika
     *     @param zipLabel Tekst z kodem pocztowym
     *     @param placeLabel Tekst z miejscowością
     *     @param phoneLabel Tekst z numerem telefonu
     *     @param wrongPdfLabel Tekst wyświetlający się w przypadku błędu w panelu generowania PDF
     *     @param pdfChooseDataComboBox Lista rozwiajana do wyboru statusu zadania
     *     @param mainAnchorPane Główne okno aplikacji
     *     @param myTaskDescription Kolumna z opisem zadania w panelu moich zadań
     *     @param myTaskEdit Kolumna z edycją zadania w panelu moich zadań
     *     @param myTaskPlannedDate Kolumna z planowaną datą zakończenia zadania w panelu moich zadań
     *     @param myTaskStatus Kolumna z statusem zadania w panelu moich zadań
     *     @param myTaskTitle Kolumna z tytułem zadania w panelu moich zadań
     *     @param myTaskTableView Tabela z moimi zadaniami w panelu moje zadania
     *     @param pdfPathField Pole tekstowe do ścieżki w panelu generowania PDF
     */
    @FXML
    private Button myTasksButton;
    @FXML
    private TextField filterField;
    @FXML
    private Button reportButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button mailEditButton;
    @FXML
    private Button passwordEditButton;
    @FXML
    private Button pdfPathButton;
    @FXML
    private Button pdfGenerateButton;
    @FXML
    private GridPane gridMyTasks;
    @FXML
    private GridPane gridReport;
    @FXML
    private GridPane gridSettings;
    @FXML
    private Label textLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label zipLabel;
    @FXML
    private Label placeLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label groupLabel;
    @FXML
    private Label wrongPdfLabel;
    @FXML
    private TableColumn<?, ?> myTaskDescription;
    @FXML
    private TableColumn<?, ?> myTaskEdit;
    @FXML
    private TableColumn<?, ?> myTaskPlannedDate;
    @FXML
    private TableColumn<?, ?> myTaskStatus;
    @FXML
    private TableColumn<?, ?> myTaskTitle;
    @FXML
    private TableView<TasksTable> myTaskTableView;
    @FXML
    private TextField pdfPathField;
    @FXML
    private ComboBox pdfChooseStatusComboBox;
    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * @param myTaskTable Lista z zadaniami w panelu moje zadania
     */
    private ObservableList<TasksTable> myTaskTable;

    /**
     * Metoda, która wykonuje się na samym początku uruchomienia się klasy. Służy do wczytania odpowiednich ustawień w panelu
     */
    @FXML
    private void initialize() {
        welcomeLabel.setText("Witaj " + UsersTable.getLoginName() + " " + UsersTable.getLoginSurname() + "!");
        gridMyTasks.toFront();
        myTask();
    }

    /**
     * Metoda do zmieniania paneli w aplikacji
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
            pdfChooseDataToGenerate();
        } else if (source == settingsButton) {
            pdfPathField.clear();
            gridSettings.toFront();
            textLabel.setText("Ustawienia");
            data();
        }
    }

    /**
     * Metoda do zarządzania wszystckih przycisków, które zmieniają całe panele oraz otwierają wyskakujące okienka
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException
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
            openWindow(mailEditButton, fxmlPath);
        } else if (source == passwordEditButton) {
            String fxmlPath = "/pop_settings/editPasswordInSettings.fxml";
            openWindow(passwordEditButton, fxmlPath);
        }
    }

    /**
     * Metoda do zarządzania tym co znajduje się w panelu raportów
     * @param event Służy do prawidłowego zarządzania okienkami
     */
    @FXML
    public void buttonReports(ActionEvent event) {
        Object source = event.getSource();
        if (source == pdfPathButton) {
            setPathPdfGenerator();
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
     * Ustawienie ścieżki do generowania PDF
     */
    private void setPathPdfGenerator() {
        final DirectoryChooser dirChooser = new DirectoryChooser();
        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();
        File file = dirChooser.showDialog(stage);

        if (file != null) {
            pdfPathField.setText(file.getAbsolutePath());
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
     * Otwieranie nowych okienek
     * @param button Przycisk, który wywołuje nowe okienko
     * @param fxml Wygląd, który ma się wyświetlić w okienku
     */
    private void openWindow(Button button, String fxml) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(button.getScene().getWindow());
            stage.setResizable(false);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ICON.png"))));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Przygotowanie wyskakującego okienka z edycją zadań
     * @param idTask Numer zadania, które chcemy edytować
     */
    private void preparePopUpWindowEditTask(String idTask) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pop_task/editTask.fxml"));
            AnchorPane anchorPane = loader.load();
            EditTaskController editTaskController = loader.getController();

            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT t.id_task, t.title, t.description, u.name, u.surname, s.name AS status, tk.planned_end FROM tasks AS t " +
                    "JOIN statuses AS s ON t.status_id = s.id_status " +
                    "JOIN users AS u ON t.user_id=u.id_user " +
                    "JOIN tasks_history AS tk ON tk.tasks_id=t.id_task " +
                    "WHERE t.id_task = " + idTask);
            result.next();

            editTaskController.setData(
                    result.getInt("id_task"),
                    result.getString("title"),
                    result.getString("description"),
                    result.getString("name"),
                    result.getString("surname"),
                    result.getString("status"),
                    result.getString("planned_end"));

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