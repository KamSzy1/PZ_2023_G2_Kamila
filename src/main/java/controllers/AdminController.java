package controllers;

import com.example.system.StageChanger;
import controllers_pop_employee.AddEmployeeController;
import controllers_pop_employee.EditEmployeeController;
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
 * Klasa służąca do zarządzania panelem administratora
 */
public class AdminController {

    /**
     * Pole tekstowe do filtrowania pracowników
     */
    @FXML
    private TextField filterEmployeeField;
    /**
     * Pole tekstowe do filtrowania moich zadań
     */
    @FXML
    private TextField filterMyTasksField;
    /**
     * Pole tekstowe do filtrowania zadań
     */
    @FXML
    private TextField filterTasksField;
    /**
     * Przycisk do przejścia do panelu z moimi zadanami
     */
    @FXML
    private Button myTasksButton;
    /**
     * Przycisk do przejścia do panelu z zadaniami
     */
    @FXML
    private Button tasksButton;
    /**
     * Przycisk do przejścia do panelu z pracownikami
     */
    @FXML
    private Button employeeButton;
    /**
     * Przycisk do przejścia do panelu z raportami
     */
    @FXML
    private Button raportButton;
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
     * Przycisk do dodania pracownika
     */
    @FXML
    private Button addEmployeeButton;
    /**
     * Przycisk do dodania zadania
     */
    @FXML
    private Button addTaskButton;
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
     * Siatka w panelu zadań
     */
    @FXML
    private GridPane gridTasks;
    /**
     * Siatka w panelu pracowników
     */
    @FXML
    private GridPane gridEmployee;
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
     * Siatka w panelu moich zadań
     */
    @FXML
    private GridPane gridMyTasks;
    /**
     * Nagłówek w odpowienidch panelach
     */
    @FXML
    private Label textLabel;
    /**
     * Tekst z przywitaniem użytkownika
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
     * Tekst wyświetlający się w przypadku błędu w panelu generowania PDF
     */
    @FXML
    private Label wrongPdfLabel;
    /**
     * Kolumna z adresami pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeeAddress;
    /**
     * Tabela z pracownikami w panelu pracowników
     */
    @FXML
    private TableView<UsersTable> employeeTableView;
    /**
     * Kolumna z numerem grupy pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeeGroup;
    /**
     * Kolumna z adresami mailowymi pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeeMail;
    /**
     * Kolumna z imionami pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeeName;
    /**
     * Kolumna z numerami telefonu pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeePhone;
    /**
     * Kolumna z stanowiskami pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeePosition;
    /**
     * Kolumna z nazwiskami pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeeSurname;
    /**
     * Kolumna z edycją pracowników w panelu pracownicy
     */
    @FXML
    private TableColumn<?, ?> employeeEdit;
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
     * Kolumna z opisem zadania w panelu zadań
     */
    @FXML
    private TableColumn<?, ?> taskDescription;
    /**
     * Kolumna z edycją zadania w panelu  zadań
     */
    @FXML
    private TableColumn<?, ?> taskEdit;
    /**
     * Kolumna z przypisanym pracownikiem do zadania w panelu  zadań
     */
    @FXML
    private TableColumn<?, ?> taskEmployee;
    /**
     * Kolumna z planowaną datą zakończenia zadania w panelu  zadań
     */
    @FXML
    private TableColumn<?, ?> taskPlannedDate;
    /**
     * Kolumna z statusem zadania w panelu  zadań
     */
    @FXML
    private TableColumn<?, ?> taskStatus;
    /**
     * Kolumna z tytułem zadania w panelu zadań
     */
    @FXML
    private TableColumn<?, ?> taskTitle;
    /**
     * Tabela z zadaniami w panelu zadania
     */
    @FXML
    private TableView<TasksTable> taskTableView;
    /**
     * Pole tekstowe do ścieżki w panelu generowania PDF
     */
    @FXML
    private TextField pdfPathField;
    /**
     * Lista rozwijana do wyboru typu generowanego PDF
     */
    @FXML
    private ComboBox pdfChooseReportComboBox;
    /**
     * Lista rozwiajana do wyboru statusu lub stabowiska pracownika w panelu raportów
     */
    @FXML
    private ComboBox pdfChooseDataComboBox;
    /**
     * Główne okno aplikacji
     */
    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * Linia czasowa do odświeżania tabel
     */
    private Timeline time;
    /**
     * Lista z moimi zadaniami
     */
    private ObservableList<TasksTable> myTaskTable;
    /**
     * Lista z zadaniami
     */
    private ObservableList<TasksTable> taskTable;
    /**
     * Lista z pracownikami
     */
    private ObservableList<UsersTable> userTable;
    /**
     * Zarządzanie przyciskami
     */
    ButtonManager buttonManager = new ButtonManager();

    /**
     * Metoda, która wykonuje się na samym początku uruchomienia się klasy. Służy do wczytania odpowiednich ustawień w panelu
     */
    @FXML
    public void initialize() {
        welcomeLabel.setText("Witaj " + UsersTable.getLoginName() + " " + UsersTable.getLoginSurname() + "!");
        gridMyTasks.toFront();
        myTask();
        task();
        pdfChooseReportToGenerate();
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
        } else if (source == settingsButton) {
            gridSettings.toFront();
            textLabel.setText("Ustawienia");
            data();
        }
    }

    /**
     * Metoda do zarządzania wszystkimi przyciskami, które zmieniają całe panele oraz otwierają wyskakujące okienka
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException Wyrzucany wyjątek
     */
    @FXML
    public void buttonsHandlerStages(ActionEvent event) throws IOException {
        StageChanger stageChanger = new StageChanger();
        Object source = event.getSource();

        if (source == logoutButton) {
            stageChanger.changeSize(915, 630);
            stageChanger.changeScene("/main.fxml");
        } else if (source == addTaskButton) {
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

            String fxmlPath = "/pop_task/addTask.fxml";
            buttonManager.openWindow(addTaskButton, fxmlPath);
        } else if (source == addEmployeeButton) {
            time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (AddEmployeeController.refBool()) {
                        refreshUser();
                        time.stop();
                        AddEmployeeController.isRefreshed = false;
                    }
                }
            }));
            time.setCycleCount(Timeline.INDEFINITE);
            time.play();

            String fxmlPath = "/pop_employee/addEmployee.fxml";
            buttonManager.openWindow(addEmployeeButton, fxmlPath);
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
                PdfGenerate.generateForAdmin(
                        pdfPathField.getText(),
                        pdfChooseReportComboBox.getSelectionModel().getSelectedItem().toString(),
                        pdfChooseDataComboBox.getSelectionModel().getSelectedItem().toString()
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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
                TasksTable myTask = new TasksTable();
                Button editButton = new Button("Edycja");
                int idTask = result.getInt("id_task");
                myTask.setEditIdTask(idTask);
                editButton.setOnAction(event -> {
                    preparePopUpWindowEditTask(String.valueOf(idTask));
                });
                myTask.setIdTask(result.getInt("id_task"));
                myTask.setTitle(result.getString("title"));
                myTask.setData(result.getDate("planned_end"));
                myTask.setDescription(result.getString("description"));
                myTask.setNameStatus(result.getString("name"));
                myTask.setEditTaskButton(editButton);
                myTaskTable.add(myTask);
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

                if (myTask.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (myTask.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (myTask.getNameStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
     * Metoda do wyświetlania danych w panelu zadania oraz do filtrowania ich
     */
    private void task() {
        try {
            DatabaseConnector.connect();
            taskTable = FXCollections.observableArrayList();

            ResultSet result = QExecutor.executeSelect("SELECT * FROM tasks " +
                    "JOIN statuses ON tasks.status_id = statuses.id_status " +
                    "JOIN users ON tasks.user_id=users.id_user " +
                    "JOIN tasks_history ON tasks_history.tasks_id=tasks.id_task");

            while (result.next()) {
                TasksTable task = new TasksTable();
                Button editButton = new Button("Edycja");
                String idTask = result.getString("id_task");
                editButton.setOnAction(event -> {
                    preparePopUpWindowEditTask(idTask);
                });
                task.setIdTask(result.getInt("id_task"));
                task.setTitle(result.getString("title"));
                task.setDescription(result.getString("description"));
                task.setData(result.getDate("tasks_history.planned_end"));
                task.setNameStatus(result.getString("name"));
                task.setNameUser(result.getString("users.name") + " " + result.getString("users.surname"));
                task.setEditTaskButton(editButton);
                taskTable.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        taskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskPlannedDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("nameStatus"));
        taskEmployee.setCellValueFactory(new PropertyValueFactory<>("nameUser"));
        taskEdit.setCellValueFactory(new PropertyValueFactory<>("editTaskButton"));

        taskTableView.setItems(taskTable);
        // Filtrowanie danych
        FilteredList<TasksTable> filteredData = new FilteredList<>(taskTable, b -> true);
        filterTasksField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tasks -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (tasks.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (tasks.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (tasks.getNameUser().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (tasks.getNameStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
                    "JOIN login ON users.id_user = login.user_id");

            while (result.next()) {
                UsersTable user = new UsersTable();
                Button editButton = new Button("Edycja");
                String tokenName = result.getString("token");
                editButton.setOnAction(event -> {
                    preparePopUpWindowEditEmployee(tokenName);
                });
                user.setName(result.getString("name"));
                user.setSurname(result.getString("surname"));
                user.setEmail(result.getString("email"));
                user.setAddress(result.getString("address"));
                user.setPhoneNumber(result.getInt("phone_num"));
                user.setNamePosition(result.getString("position_name"));
                user.setGroups(result.getInt("groups"));
                user.setEditEmployeeButton(editButton);
                userTable.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        employeePosition.setCellValueFactory(new PropertyValueFactory<>("namePosition"));
        employeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        employeeMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeePhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        employeeGroup.setCellValueFactory(new PropertyValueFactory<>("groups"));
        employeeEdit.setCellValueFactory(new PropertyValueFactory<>("editEmployeeButton"));

        employeeTableView.setItems(userTable);

        FilteredList<UsersTable> filteredData = new FilteredList<>(userTable, b -> true);
        filterEmployeeField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
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
        SortedList<UsersTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTableView.comparatorProperty());
        employeeTableView.setItems(sortedData);
    }

    /**
     * Odświeżanie tabeli użytkowników
     */
    private void refreshUser() {
        userTable.clear();
        employee();
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
            //SELECT t.title, t.description, u.name, u.surname, s.name, tk.planned_end FROM tasks AS t JOIN statuses AS s ON t.status_id = s.id_status JOIN users AS u ON t.user_id=u.id_user JOIN tasks_history AS tk ON tk.tasks_id=t.id_task WHERE t.id_task = 8
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
     * Przygotowanie wyskakującego okienka z edycją pracownika
     *
     * @param token Token na podstawie którego wybieramy, jakiego pracownika chcemy edytować
     */
    private void preparePopUpWindowEditEmployee(String token) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pop_employee/editEmployee.fxml"));
            AnchorPane anchorPane = loader.load();
            EditEmployeeController editEmployeeController = loader.getController();

            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT u.id_user, u.name, u.surname, u.address, u.place, u.zip, u.phone_num, p.id_position, p.position_name, u.token, u.groups FROM users AS u " +
                    "JOIN positions AS p ON u.position_id = p.id_position " +
                    "WHERE u.token = '" + token + "'");
            result.next();

            editEmployeeController.setData(
                    result.getInt("id_user"),
                    result.getString("name"),
                    result.getString("surname"),
                    result.getString("phone_num"),
                    result.getString("place"),
                    result.getString("address"),
                    result.getString("groups"),
                    result.getInt("id_position"),
                    result.getString("position_name"),
                    result.getString("token"),
                    result.getString("zip")
            );

            time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (EditEmployeeController.refBool()) {
                        refreshEmployee();
                        time.stop();
                        EditEmployeeController.isRefreshed = false;
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
     * Odświeżanie pracowników
     */
    private void refreshEmployee() {
        userTable.clear();
        employee();
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
