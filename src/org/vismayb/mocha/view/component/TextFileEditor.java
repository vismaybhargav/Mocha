package org.vismayb.mocha.frontend.component;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import org.vismayb.mocha.backend.model.EditorModel;
import org.vismayb.mocha.view.util.ColorHelperKt;

import java.io.File;

public class TextFileEditor extends ScrollPane {
    /** Currently the "model" */
    private final EditorModel model;
    /** Should not be made new, children should be cleared and repopulated with {@code recreateFileView()}*/
    private final VBox lineContainer = new VBox();
    private final VBox gutter = new VBox();

    public TextFileEditor(File file) {
        super();


        // Initialize the buffer before because we don't want to do extra checks in other methods.
        model = new EditorModel(file);

        for(int i =  0; i < model.getLines().size(); i++) {
            var t = new Text(Integer.toString(i + 1));
            t.setFont(Font.font("JetBrains Mono", 15));
            t.setFill(ColorHelperKt.generateColor(95, 99, 102));
            t.setFontSmoothingType(FontSmoothingType.LCD);
            gutter.getChildren().add(t);
        }

        lineContainer.setBackground(Background.fill(ColorHelperKt.generateColor(41, 42, 47)));
        //lineContainer.setBackground(Background.fill(Color.GREEN));

        HBox hbox = new HBox();
        hbox.getChildren().addAll(gutter, lineContainer);
        //hbox.setSpacing(10);
        setContent(hbox);

        applyGutterConfigs();
        recreateFileView(); // Can be called the first time.
    }

    /**
     * Recreates the entire text component. Clears the line container
     * and repopulates with new lines.
     */
    public void recreateFileView() {
        lineContainer.getChildren().clear();
        //TODO: Refresh the model over here

        System.out.println(model.getLines().get(0));
        for(int i = 0; i <= model.getLines().size(); i++) {
            var tokensInLine = model.getTokensByLineNumber(i);
            EditorLine line = new EditorLine(model.getLines().get(i), tokensInLine, i + 1);
            lineContainer.getChildren().add(line);
        }
    }

    private void applyGutterConfigs() {
        gutter.setBackground(Background.fill(ColorHelperKt.generateColor(41, 42, 47)));
        gutter.setPadding(new Insets(0, 20, 10, 5));
        gutter.setBorder(Border.stroke(ColorHelperKt.generateColor(55, 55, 55)));
    }
}