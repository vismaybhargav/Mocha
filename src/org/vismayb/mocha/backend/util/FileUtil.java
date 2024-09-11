package org.vismayb.mocha.backend.util;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtil {
    public static String readTextAsLines(final File file) {
        StringBuilder text = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        }

        return text.toString();
    }

    public static ArrayList<String> getAllLines(final File file) {
        ArrayList<String> al = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                al.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        }

        return al;
    }

    public static String[] getAllLines(final StringBuilder sb) {
        return sb.toString().split("\n");
    }

    public static StringBuilder getAllLinesAsStringBuilder(final File file) {
        StringBuilder text = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        }

        return text;
    }

    public static int getLineCount(final File file) {
        // get the number of lines in a file, this is more generic for LF and CRLF
        int count = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            count = -1;
        }
        return count;
    }

    public static int getLineCount(final String str) {
        // limit set to -1 to ensure lines with only \n are counted
        return str.split("\n", -1).length;
    }

    public static File getFileFromUser() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open File");
        return fc.showOpenDialog(null);
    }

    public static void saveFile(File file, String textToSave) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(textToSave);
            showAlert("Saved Successfully", "File saved successfully");
        } catch (IOException e) {
            System.out.println("IOException occurred" + e.getMessage());
        }
    }

    // shows an alert dialog used for saving files
    private static void showAlert(final String title, final String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
