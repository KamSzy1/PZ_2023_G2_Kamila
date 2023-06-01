package pdf_generate;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DatabaseConnector;
import database.QExecutor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;

public class PdfGenerate {

    private static String table = "";

    public static void generateForEmployee(String path, String pdfData, int idUser) {
        String query = setQueryForEmployee(pdfData, idUser);
        generatePdfTasks(query, path);
    }

    private static String setQueryForEmployee(String pdfData, int idUser) {
        if (pdfData.equals("Wszystko")) {
            return "SELECT * FROM tasks " +
                    "INNER JOIN users ON users.id_user = tasks.user_id " +
                    "WHERE users.id_user = " + idUser;
        }
        else {
            return "SELECT * FROM tasks " +
                    "INNER JOIN users ON users.id_user = tasks.user_id " +
                    "INNER JOIN statuses ON statuses.id_status = tasks.status_id " +
                    "WHERE users.id_user = " + idUser + " " +
                    "AND statuses.name = '" + pdfData + "'";
        }
    }

    public static void generateForManager(String path, String pdfType, String pdfData, int group) {
        String query = setQueryForManager(pdfType, pdfData, group);
        generateGoodPdfManager(query, path);
    }

    private static String setQueryForManager(String pdfType, String pdfData, int group) {
        String query = "";

        switch (pdfType) {
            case "Zadania" -> {
                query = "SELECT title, description FROM tasks " +
                        "INNER JOIN statuses ON statuses.id_status = tasks.status_id " +
                        "INNER JOIN users ON users.id_user = tasks.user_id " +
                        "WHERE statuses.name = '" + pdfData + "' " +
                        "AND users.groups = " + group;
                table = "tasks";
            }
            case "Pracownicy" -> {
                query = "SELECT name, surname FROM users " +
                        "INNER JOIN positions ON positions.id_position = users.position_id " +
                        "WHERE positions.position_name = '" + pdfData + "' " +
                        "AND users.groups = " + group;
                table = "users";
            }
        }

        if (pdfData.equals("Wszystko")) {
            if (table.equals("tasks")) {
                return "SELECT * FROM " + table + " " +
                        "INNER JOIN users ON users.id_user = tasks.user_id " +
                        "WHERE users.groups = " + group;
            } else if (table.equals("users")) {
                return "SELECT * FROM " + table + " " +
                        "WHERE users.groups = " + group;
            }
        }

        System.out.println("Manager: " + query);
        return query;
    }

    public static void generateForAdmin(String path, String pdfType, String pdfData) {
        String query = setQueryForAdmin(pdfType, pdfData);
        generateGoodPdf(query, path);
    }

    private static String setQueryForAdmin(String pdfType, String pdfData) {
        String query = "";

        switch (pdfType) {
            case "Zadania" -> {
                query = "SELECT title, description FROM tasks INNER JOIN statuses ON statuses.id_status = tasks.status_id WHERE statuses.name = '" + pdfData + "'";
                table = "tasks";
            }
            case "Pracownicy" -> {
                query = "SELECT name, surname FROM users INNER JOIN positions ON positions.id_position = users.position_id WHERE positions.position_name = '" + pdfData + "'";
                table = "users";
            }
        }

        if (pdfData.equals("Wszystko")) {
            return "SELECT * FROM " + table;
        }

        System.out.println("Admin: " + query);
        return query;
    }

    private static void generateGoodPdf(String query, String path) {
        if (table.equals("tasks")) {
            generatePdfTasks(query, path);
        } else if (table.equals("users")) {
            generatePdfEmployee(query, path);
        }
    }

    private static void generateGoodPdfManager(String query, String path) {
        if (table.equals("tasks")) {
            generatePdfTasks(query, path);
        } else if (table.equals("users")) {
            generatePdfEmployee(query, path);
        }
    }

    private static void generatePdfEmployee(String query, String path) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\Pracownicy.pdf"));
            document.open();

            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);

            PdfPCell titleCell = new PdfPCell(new Phrase("Lista pracowników"));
            titleCell.setBackgroundColor(new BaseColor(85, 73, 148)); // Fioletowe tło
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setPadding(20);
            titleTable.addCell(titleCell);
            titleTable.setSpacingAfter(20f); // Ustawienie odstępu po tytule

            document.add(titleTable);

            PdfPTable table = new PdfPTable(3); // Liczba kolumn

            table.addCell("Numer");
            table.addCell("Imie");
            table.addCell("Nazwisko");

            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect(query);

            int counter = 1;
            while (result.next()) {
                String idNumber = String.valueOf(counter);
                String firstName = result.getString("name");
                String lastName = result.getString("surname");

                table.addCell(idNumber);
                table.addCell(firstName);
                table.addCell(lastName);
                counter++;
            }

            document.add(table);
            document.close();
        } catch (SQLException | DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void generatePdfTasks(String query, String path) {
        try {
            // Tworzenie dokumentu PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\Zadania.pdf"));

            // Otwarcie dokumentu
            document.open();

            // Dodawanie tytułu z tłem
            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);

            PdfPCell titleCell = new PdfPCell(new Phrase("Lista zadań"));
            titleCell.setBackgroundColor(new BaseColor(85, 73, 148)); // Fioletowe tło
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setPadding(20);
            titleTable.addCell(titleCell);
            titleTable.setSpacingAfter(20f); // Ustawienie odstępu po tytule

            document.add(titleTable);

            DatabaseConnector.connect();
            ResultSet resultSet = QExecutor.executeSelect(query);

            // Tworzenie tabeli
            PdfPTable table = new PdfPTable(3); // Liczba kolumn

            // Dodawanie nagłówków tabeli
            table.addCell("Numer");
            table.addCell("Tytuł");
            table.addCell("Opis");

            int counter = 1;
            while (resultSet.next()) {
                String idTask = String.valueOf(counter);
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                table.addCell(idTask);
                table.addCell(title);
                table.addCell(description);
                counter++;
            }

            resultSet.close();

            // Dodawanie tabeli do dokumentu
            document.add(table);

            // Zamykanie dokumentu
            document.close();

            System.out.println("Plik PDF został utworzony.");
        } catch (SQLException | DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}