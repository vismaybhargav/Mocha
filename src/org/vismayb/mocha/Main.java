package org.vismayb.mocha;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.vismayb.mocha.backend.util.FileUtil;
import org.vismayb.mocha.component.TextFileEditor;

import java.io.File;

public class Main extends Application {
    private final BorderPane bPane = new BorderPane();
    private StringBuilder sb;
    private File fileToEdit;
    private TextFileEditor textFileEditor;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        applyStageConfigs(primaryStage);

        MenuBar mBar = createMenuBar();
        bPane.setTop(mBar);
        Scene scene = new Scene(bPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar mBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem fileOpenItem = new MenuItem("Open File");

        fileOpenItem.setOnAction(_ -> {
            fileToEdit = FileUtil.getFileFromUser();

            textFileEditor = new TextFileEditor(fileToEdit);

            bPane.setCenter(textFileEditor);
        });

        mBar.getMenus().add(fileMenu);
        fileMenu.getItems().add(fileOpenItem);

        return mBar;
    }

    private void applyStageConfigs(Stage stage) {
        stage.setTitle("File Reader Test");
        stage.setMinHeight(600);
        stage.setMinWidth(800);
    }
}
