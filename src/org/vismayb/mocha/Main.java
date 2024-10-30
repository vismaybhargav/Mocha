package org.vismayb.mocha;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.vismayb.mocha.backend.util.FileUtil;
import org.vismayb.mocha.view.component.TextFileEditor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private final BorderPane bPane = new BorderPane();
    private File fileToEdit;
    private TextFileEditor textFileEditor;

    public static void main(String[] args) {
        parseArgs(Arrays.asList(args));
        FileUtil.purgeDirectory(new File("logs"));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        applyStageConfigs(primaryStage);

        MenuBar mBar = createMenuBar();
        bPane.setTop(mBar);
        Scene scene = new Scene(bPane);

        if(GlobalConstants.Companion.getDefaultFile() != null) {
            textFileEditor = new TextFileEditor(GlobalConstants.Companion.getDefaultFile());
            bPane.setCenter(textFileEditor);
        }

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
        stage.setMinHeight(GlobalConstants.Companion.getHeight());
        stage.setMinWidth(GlobalConstants.Companion.getWidth());
    }

    private static void parseArgs(List<String> args) {
        if(args.contains("-withLogs")) GlobalConstants.Companion.setLoggingEnabled(true);
        if(args.contains("-logToFile")) GlobalConstants.Companion.setLogToFile(true);
        if(args.contains("-defaultFile")) GlobalConstants.Companion.setDefaultFile(
                new File(args.get(args.indexOf("-defaultFile") + 1))
        );
        if(args.contains("-devModeEnabled")) GlobalConstants.Companion.setDevMode(true);
    }
}
