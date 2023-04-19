package com.example.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class StageChanger extends Application {

    private static javafx.stage.Stage stage;

    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main.fxml")));
            Scene scene = new Scene(root);

            stage = primaryStage;
            primaryStage.setResizable(false);
            primaryStage.setTitle("SysTrack");
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ICON.png"))));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Zmiana wielkości panelu
    public void changeSize(int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    //Zmiana panelu
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stage.getScene().setRoot(pane);
    }

}
