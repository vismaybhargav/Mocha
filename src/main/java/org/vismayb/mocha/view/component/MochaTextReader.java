package org.vismayb.mocha.view.component;

import javafx.scene.layout.*;
import org.vismayb.mocha.GlobalConstants;
import org.vismayb.mocha.backend.model.EditorModel;
import org.vismayb.mocha.logging.LoggingService;
import org.vismayb.mocha.view.Theme;
import org.vismayb.mocha.view.component.editor.EditorLine;
import org.vismayb.mocha.view.component.editor.TextLine;

import java.io.File;

public class MochaTextReader extends StackPane {
    /** Currently the "model" */
    private final EditorModel model;
    /** Should not be made new, children should be cleared and repopulated with {@code recreateFileView()}*/
    private final VBox lineContainer = new VBox();
    private final VBox gutterContainer = new VBox();
    private final LoggingService loggingService = new LoggingService("logs/LogFile.txt");

    public MochaTextReader(File file, Theme theme) {
        super();

        GlobalConstants.Companion.setTheme(theme);

        // Initialize the buffer before because we don't want to do extra checks in other methods.
        model = new EditorModel(file);

        /*
        for(int i =  0; i < model.getLines().size(); i++) {
            gutterContainer.getChildren().add(
                    StyledText.getDefaultStyle(Integer.toString(i + 1), theme.getGutterForeground()));
        }*/

        lineContainer.setBackground(Background.fill(GlobalConstants.Companion.getTheme().getEditorBackground()));
        gutterContainer.setBackground(Background.fill(GlobalConstants.Companion.getTheme().getEditorBackground()));

        var hbox = new HBox();
        hbox.getChildren().addAll(gutterContainer, lineContainer);
        getChildren().add(hbox);


        recreateFileView(); // Can be called the first time.
    }

    /**
     * Recreates the entire text component. Clears the line container
     * and repopulates with new lines.
     */
    public void recreateFileView() {
        lineContainer.getChildren().clear();

        // This for loops bound should never be changed as it would affect the sync between model and view
        for(int i = 0; i < model.getLines().size(); i++) {
            var tokensInLine = model.getTokensByLineNumber(i);
            //var line = new TextLine(model.getLines().get(i), tokensInLine, i + 1);
            var line = new EditorLine(model.getLines().get(i), tokensInLine, i + 1);
            HBox.setHgrow(line, Priority.ALWAYS);
            lineContainer.getChildren().add(line);

            if(!GlobalConstants.Companion.isLoggingEnabled()) continue;
            // Log the appropriate log information
            loggingService.rewriteIntermediateBuffer(TextLine.logFileBuilder.toString());
            loggingService.writeLog(true);
        }
    }   
}
