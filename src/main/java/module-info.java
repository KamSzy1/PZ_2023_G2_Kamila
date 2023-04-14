module com.example.system {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.validator;
    requires lombok;
    requires java.sql;


    opens com.example.system to javafx.fxml;
    exports com.example.system;

    opens controllers to javafx.fxml;
    exports controllers;

}