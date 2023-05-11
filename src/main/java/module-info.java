module com.example.system {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.validator;
    requires lombok;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.system to javafx.fxml;
    exports com.example.system;

    opens controllers to javafx.fxml;
    exports controllers;

    opens database_classes to javafx.fxml;
    exports database_classes;
    exports controllers_popup;
    opens controllers_popup to javafx.fxml;
}