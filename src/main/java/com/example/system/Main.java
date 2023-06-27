package com.example.system;

import javafx.application.Application;
import java.sql.SQLException;

import static database.DatabaseSetter.databaseSetter;

/**
 * Klasa uruchamiająca aplikację
 */
public class Main {
    /**
     * Main
     *
     * @param args Argumenty dla metody
     */
    public static void main(String[] args) {
        try {
            databaseSetter();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Application.launch(StageChanger.class, args);
    }
}