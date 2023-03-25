package com.example.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
        stage = primaryStage;
        primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainPage.fxml")));
        primaryStage.setTitle("SysTrack");
        Scene scene = new Scene(root, 680, 485);
        primaryStage.setScene(scene);
        primaryStage.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Zmiana panelu/sceny
    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
       launch();
    }
}