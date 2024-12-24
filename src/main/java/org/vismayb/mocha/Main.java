package org.vismayb.mocha;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.vismayb.mocha.backend.util.FileUtilKt;
import org.vismayb.mocha.view.component.MochaTextReader;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private final BorderPane bPane = new BorderPane();
    private File fileToEdit;
    private MochaTextReader mochaTextReader;

    public static void main(String[] args) {
        parseArgs(Arrays.asList(args));
        if(GlobalConstants.Companion.isLoggingEnabled()) FileUtilKt.purgeDirectory(new File("logs"));

        // Add a reflection based solver to the parser configuration so that we can resolve symbols accurately
        StaticJavaParser
                .getParserConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(new CombinedTypeSolver(new ReflectionTypeSolver())));

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        applyStageConfigs(primaryStage);

        MenuBar mBar = createMenuBar();
        bPane.setTop(mBar);
        var scene = new Scene(bPane);

        if(GlobalConstants.Companion.getDefaultFile() != null) {
            mochaTextReader = new MochaTextReader(
                    GlobalConstants.Companion.getDefaultFile(),
                    GlobalConstants.Companion.getTheme()
            );
            bPane.setCenter(mochaTextReader);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        var mBar = new MenuBar();

        var fileMenu = new Menu("File");
        var fileOpenItem = new MenuItem("Open File");

        fileOpenItem.setOnAction(_ -> {
            fileToEdit = FileUtilKt.getFileFromUser();

            mochaTextReader = new MochaTextReader(fileToEdit, GlobalConstants.Companion.getTheme());

            bPane.setCenter(mochaTextReader);
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
