package org.vismayb.mocha.frontend.component;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.vismayb.mocha.backend.util.ColorHelperKt;
import org.vismayb.mocha.backend.util.FileUtil;

import java.io.File;
import java.util.ArrayList;

public class TextFileEditor extends ScrollPane {
    /** The file that this component works around */
    private final File file;
    /** Currently the "model" */
    private final StringBuilder buffer;
    /** Should not be made new, children should be cleared and repopulated with {@code recreateFileView()}*/
    private final VBox lineContainer = new VBox();
    private final VBox gutter = new VBox();
    private final ArrayList<String> lines;
    private Cursor cursor;

    public TextFileEditor(File file) {
        super();

        this.file = file;
        lines = FileUtil.getAllLines(file);
        cursor = new Cursor(new Point2D(0, 0));

        // Initialize the buffer before because we don't want to do extra checks in other methods.
        buffer = FileUtil.getAllLinesAsStringBuilder(file);

        for(int i =  0; i < lines.size(); i++) {
            var t = new Text(Integer.toString(i + 1));
            t.setFont(Font.font("JetBrains Mono", 20));
            gutter.getChildren().add(t);
        }

        lineContainer.setBackground(Background.fill(ColorHelperKt.generateColor(30, 31, 34)));
        //lineContainer.setBackground(Background.fill(Color.GREEN));

        HBox hbox = new HBox();
        hbox.getChildren().addAll(gutter, lineContainer);
        hbox.setSpacing(10);
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

        for(int i = 0; i < lines.size(); i++) {
            lineContainer.getChildren().add(new EditorLine(lines.get(i), i + 1));
        }
    }

    private void applyGutterConfigs() {
        gutter.setBackground(Background.fill(Color.GRAY));
        gutter.setPadding(new Insets(0, 0, 10, 10));
    }
}