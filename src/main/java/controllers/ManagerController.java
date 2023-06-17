package controllers;

import com.example.system.StageChanger;
import controllers_pop_task.AddTaskController;
import controllers_pop_task.EditTaskController;
import database.DatabaseConnector;
import database.QExecutor;
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
import javafx.util.Duration;

import pdf_generate.PdfGenerate;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Klasa służąca do zarządzania panelem kierownika
 */

public class ManagerController {

    /**
     * Potrzebne zmienne z Scene Buildera, aby aplikacja działała poprawnie
     *
     *     @param filterEmployeeField Pole tekstowe do filtrowania pracowników
     *     @param filterMyTasksField Pole tekstowe do filtrowania moich zadań
     *     @param filterTasksField Pole tekstowe do filtrowania zadań
     *     @param myTasksButton Przycisk do przejścia do panelu z moimi zadanami
     *     @param tasksButton Przycisk do przejścia do panelu z zadaniami
     *     @param employeeButton Przycisk do przejścia do panelu z pracownikami
     *     @param raportButton Przycisk do przejścia do panelu z raportami
     *     @param settingsButton Przycisk do przejścia do panelu z ustawieniami
     *     @param logoutButton Przycisk do wylogowania się
     *     @param addEmployeeButton Przycisk do dodania pracownika
     *     @param addTaskButton Przycisk do dodania zadania
     *     @param pdfPathButton Przycisk do ustawienia ścieżki do generowania PDF
     *     @param pdfGenerateButton Przycisk do generowania PDF
     *     @param mailEditButton Przycisk do edycji maila
     *     @param passwordEditButton Przycisk do edycji hasła
     *     @param gridTasks Siatka w panelu zadań
     *     @param gridEmployee Siatka w panelu pracowników
     *     @param gridReport Siatka w panelu raportów
     *     @param gridSettings Siatka w panelu ustawień
     *     @param gridMyTasks Siatka w panelu moich zadań
     *     @param textLabel Nagłówek w odpowienidch panelach
     *     @param welcomeLabel Tekst z nazwą użytkownika
     *     @param nameLabel Tekst z imieniem pracownika
     *     @param surnameLabel Tekst z nazwiskiem pracownika
     *     @param addressLabel Tekst z adresem pracownika
     *     @param zipLabel Tekst z kodem pocztowym
     *     @param placeLabel Tekst z miejscowością
     *     @param phoneLabel Tekst z numerem telefonu
     *     @param wrongPdfLabel Tekst wyświetlający się w przypadku błędu w panelu generowania PDF
     *     @param employeeAddress Kolumna z adresami pracowników w panelu pracownicy
     *     @param employeeTableView Tabela z pracownikami w panelu pracowników
     *     @param employeeGroup Kolumna z numerem grupy pracowników w panelu pracownicy
     *     @param employeeMail Kolumna z adresami mailowymi pracowników w panelu pracownicy
     *     @param employeeName Kolumna z imionami pracowników w panelu pracownicy
     *     @param employeePhone Kolumna z numerami telefonu pracowników w panelu pracownicy
     *     @param employeePosition Kolumna z stanowiskami pracowników w panelu pracownicy
     *     @param employeeSurname Kolumna z nazwiskami pracowników w panelu pracownicy
     *     @param myTaskDescription Kolumna z opisem zadania w panelu moich zadań
     *     @param myTaskEdit Kolumna z edycją zadania w panelu moich zadań
     *     @param myTaskPlannedDate Kolumna z planowaną datą zakończenia zadania w panelu moich zadań
     *     @param myTaskStatus Kolumna z statusem zadania w panelu moich zadań
     *     @param myTaskTitle Kolumna z tytułem zadania w panelu moich zadań
     *     @param myTaskTableView Tabela z moimi zadaniami w panelu moje zadania
     *     @param taskDescription Kolumna z opisem zadania w panelu zadań
     *     @param taskEdit Kolumna z edycją zadania w panelu  zadań
     *     @param taskEmployee Kolumna z przypisanym pracownikiem do zadania w panelu  zadań
     *     @param taskPlannedDate Kolumna z planowaną datą zakończenia zadania w panelu  zadań
     *     @param taskStatus Kolumna z statusem zadania w panelu  zadań
     *     @param taskTitle Kolumna z tytułem zadania w panelu zadań
     *     @param taskTableView Tabela z zadaniami w panelu zadania
     *     @param pdfPathField Pole tekstowe do ścieżki w panelu generowania PDF
     *     @param pdfChooseReportComboBox Lista rozwijana do wyboru typu generowanego PDF
     *     @param pdfChooseDataComboBox Lista rozwiajana do wyboru statusu lub stabowiska pracownika w panelu raportów
     *     @param mainAnchorPane Główne okno aplikacji
     */

    @FXML
    private TextField filterEmployeeField;
    @FXML
    private TextField filterMyTasksField;
    @FXML
    private TextField filterTasksField;
    @FXML
    private Button myTasksButton;
    @FXML
    private Button tasksButton;
    @FXML
    private Button employeeButton;
    @FXML
    private Button raportButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button addTaskButton;
    @FXML
    private Button mailEditButton;
    @FXML
    private Button pdfPathButton;
    @FXML
    private Button pdfGenerateButton;
    @FXML
    private Button passwordEditButton;
    @FXML
    private GridPane gridTasks;
    @FXML
    private TableColumn<?, ?> employeeAddress;
    @FXML
    private TableColumn<?, ?> employeeGroup;
    @FXML
    private TableColumn<?, ?> employeeMail;
    @FXML
    private TableColumn<?, ?> employeeName;
    @FXML
    private TableColumn<?, ?> employeePhone;
    @FXML
    private TableColumn<?, ?> employeePosition;
    @FXML
    private TableColumn<?, ?> employeeSurname;
    @FXML
    private TableView<UsersTable> employeeTableView;
    @FXML
    private GridPane gridEmployee;
    @FXML
    private GridPane gridReport;
    @FXML
    private GridPane gridSettings;
    @FXML
    private GridPane gridMyTasks;
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
    private TableView<TasksTable> taskTableView;
    @FXML
    private TableColumn<TasksTable, String> taskDescription;
    @FXML
    private TableColumn<TasksTable, ?> taskEdit;
    @FXML
    private TableColumn<TasksTable, Integer> taskEmployee;
    @FXML
    private TableColumn<TasksTable, ?> taskPlannedDate;
    @FXML
    private TableColumn<TasksTable, Integer> taskStatus;
    @FXML
    private TableColumn<TasksTable, String> taskTitle;
    @FXML
    private TextField pdfPathField;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private ComboBox pdfChooseReportComboBox;
    @FXML
    private ComboBox pdfChooseDataComboBox;

    /**
     * Zmienne potrzebne do prawidłowego działania aplikacji
     *
     * @param time Linia czasowa do odświeżania tabel
     * @param myTaskTable Lista z moimi zadaniami
     * @param taskTable Lista z zadaniami
     * @param userTable Lista z pracownikami
     */
    private Timeline time;
    private ObservableList<TasksTable> myTaskTable;
    private ObservableList<TasksTable> taskTable;
    private ObservableList<UsersTable> userTable;

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
            myTask();
        } else if (source == tasksButton) {
            gridTasks.toFront();
            textLabel.setText("Zadania");
            task();
        } else if (source == employeeButton) {
            gridEmployee.toFront();
            textLabel.setText("Pracownicy");
            employee();
        } else if (source == raportButton) {
            pdfPathField.clear();
            gridReport.toFront();
            textLabel.setText("Generowanie raportów");
            wrongPdfLabel.setText("");
            pdfChooseReportToGenerate();
        } else if (source == settingsButton) {
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
        } else if (source == addTaskButton) {
            String fxmlPath = "/pop_task/addTask.fxml";
            openWindow(addTaskButton, fxmlPath);
            time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (AddTaskController.refBool()) {
                        refreshTask();
                        time.stop();
                        AddTaskController.isRefreshed = false;
                    }
                }
            }));
            time.setCycleCount(Timeline.INDEFINITE);
            time.play();

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
                PdfGenerate.generateForManager(
                        pdfPathField.getText(),
                        pdfChooseReportComboBox.getSelectionModel().getSelectedItem().toString(),
                        pdfChooseDataComboBox.getSelectionModel().getSelectedItem().toString(),
                        UsersTable.getGroupNumber()
                );
                wrongPdfLabel.setText("PDF został wygenerowany");
            } else {
                wrongPdfLabel.setText("Ustaw ścieżkę zapisu PDF");
            }
        }
    }

    /**
     * Odświeżanie tabeli zadań
     */
    private void refreshTask() {
        taskTable.clear();
        task();
    }

    /**
     * Odświeżanie edycji zadań
     */
    private void refreshEditTask() {
        myTaskTable.clear();
        myTask();
        taskTable.clear();
        task();
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
                TasksTable task = new TasksTable();

                Button editButton = new Button("Edycja");
                String idTask = result.getString("id_task");
                editButton.setOnAction(event -> {
                    preparePopUpWindowEditTask(idTask);
                });

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
        filterMyTasksField.textProperty().addListener((observable, oldValue, newValue1) -> {
            filteredTaskData.setPredicate(myTask -> {
                if (newValue1 == null || newValue1.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue1.toLowerCase();

                if (myTask.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (myTask.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (myTask.getNameStatus().toLowerCase().contains(lowerCaseFilter)) {
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
                groupLabel.setText(String.valueOf(UsersTable.getGroupNumber()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do wyświetlania danych w panelu zadania oraz do filtrowania ich
     */
    private void task() {
        try {
            DatabaseConnector.connect();
            taskTable = FXCollections.observableArrayList();

            ResultSet result = QExecutor.executeSelect("SELECT * FROM tasks " +
                    "JOIN statuses ON tasks.status_id = statuses.id_status " +
                    "JOIN users ON tasks.user_id=users.id_user " +
                    "JOIN tasks_history ON tasks_history.tasks_id=tasks.id_task" +
                    " WHERE groups = " + UsersTable.getGroupNumber());

            while (result.next()) {
                TasksTable task = new TasksTable();
                Button editButton = new Button("Edycja");
                String idTask = result.getString("id_task");
                editButton.setOnAction(event -> {
                    preparePopUpWindowEditTask(idTask);
                });
                task.setTitle(result.getString("title"));
                task.setDescription(result.getString("description"));
                task.setData(result.getDate("tasks_history.planned_end"));
                task.setNameStatus(result.getString("name"));
                task.setNameUser(result.getString("users.name"));
                task.setEditTaskButton(editButton);
                taskTable.add(task);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        taskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskPlannedDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("nameStatus"));
        taskEmployee.setCellValueFactory(new PropertyValueFactory<>("nameUser"));
        taskEdit.setCellValueFactory(new PropertyValueFactory<>("editTaskButton"));
        taskTableView.setItems(taskTable);

        FilteredList<TasksTable> filteredData = new FilteredList<>(taskTable, b -> true);
        filterTasksField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tasks -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (tasks.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tasks.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tasks.getNameUser().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tasks.getNameStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<TasksTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(taskTableView.comparatorProperty());
        taskTableView.setItems(sortedData);
    }


    /**
     * Metoda do wyświetlania danych w panelu pracownicy oraz do filtrowania ich
     */
    private void employee() {
        try {
            DatabaseConnector.connect();
            userTable = FXCollections.observableArrayList();

            ResultSet result = QExecutor.executeSelect("SELECT * FROM users " +
                    "JOIN positions ON users.position_id = positions.id_position " +
                    "JOIN login ON users.id_user = login.user_id " +
                    "WHERE groups = " + UsersTable.getGroupNumber());

            while (result.next()) {
                UsersTable user = new UsersTable();
                user.setName(result.getString("name"));
                user.setSurname(result.getString("surname"));
                user.setAddress(result.getString("address"));
                user.setEmail(result.getString("email"));
                user.setPhoneNumber(result.getInt("phone_num"));
                user.setNamePosition(result.getString("position_name"));
                user.setGroups(result.getInt("groups"));
                userTable.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        employeePosition.setCellValueFactory(new PropertyValueFactory<>("namePosition"));
        employeeMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        employeePhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        employeeGroup.setCellValueFactory(new PropertyValueFactory<>("groups"));
        employeeTableView.setItems(userTable);

        FilteredList<UsersTable> filteredEmpData = new FilteredList<>(userTable, b -> true);
        filterEmployeeField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEmpData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getNamePosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(user.getPhoneNumber()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(user.getGroups()).contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<UsersTable> sortedData = new SortedList<>(filteredEmpData);
        sortedData.comparatorProperty().bind(employeeTableView.comparatorProperty());
        employeeTableView.setItems(sortedData);
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
            ResultSet result = QExecutor.executeSelect(
                    "SELECT t.id_task, t.title, t.description, u.name, u.surname, s.name AS status, tk.planned_end FROM tasks AS t " +
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
     * Wybór raportu do wygenerowania
     */
    private void pdfChooseReportToGenerate() {
        ObservableList<String> pdfReport = FXCollections.observableArrayList();

        pdfReport.add("Zadania");
        pdfReport.add("Pracownicy");
        pdfChooseReportComboBox.setItems(pdfReport);
        pdfChooseReportComboBox.setOnAction(e -> pdfChooseDataToGenerate());
    }

    /**
     * Wybranie typu generowanego PDF, jaki nas interesuje
     */
    private void pdfChooseDataToGenerate() {
        ObservableList<String> pdfData = FXCollections.observableArrayList();

        if (pdfChooseReportComboBox.getSelectionModel().getSelectedItem().equals("Zadania")) {
            pdfData.clear();
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
        } else if (pdfChooseReportComboBox.getSelectionModel().getSelectedItem().equals("Pracownicy")) {
            pdfData.clear();
            try {
                DatabaseConnector.connect();
                ResultSet rs = QExecutor.executeSelect("SELECT position_name FROM positions");
                pdfData.add("Wszystko");
                while (rs.next()) {
                    pdfData.add(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        pdfChooseDataComboBox.setItems(pdfData);
        pdfChooseDataComboBox.setPromptText("Wybierz rodzaj");
    }
}
