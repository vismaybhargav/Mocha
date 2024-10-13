package org.vismayb.mocha.view.component;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.vismayb.mocha.GlobalConstants;
import org.vismayb.mocha.Loggable;
import org.vismayb.mocha.backend.token.Token;
import org.vismayb.mocha.view.util.ColorHelperKt;

import java.util.ArrayList;
import java.util.List;

public class EditorLine extends HBox implements Loggable {
    private final String text;
    private final List<Token> tokens;
    private final int lineNumber;

    public EditorLine(final String text, final int lineNumber) {
        this(text, new ArrayList<>(), lineNumber);
    }

    public EditorLine(final String text, final List<Token> tokens, final int lineNumber) {
        this.text = text;
        this.tokens = tokens;
        this.lineNumber = lineNumber;

        generateView();
        HBox.setHgrow(this, Priority.ALWAYS);
        log();
    }

    /**
     * Generates the view for this editorLine
     */
    private void generateView() {
        // We need to add the first bit before the first token in case the first token.startOffset() != 0

        if(!tokens.isEmpty()) {
            addStringToLineContainer(text.substring(0, tokens.getFirst().getStartOffset()));
        } else {
            // We need to just make it with the text if there are no tokens on a given line.
            addStringToLineContainer(text);
            return;
        }

        // Insert each token's contents in between the last token and the curr token.
        for (var i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);

            addTokenToLineContainer(token);

            addStringToLineContainer(
                    i < tokens.size() - 1
                            // text between the end of this token and the start of the next one
                            ? text.substring(token.getEndOffset(), tokens.get(i + 1).getStartOffset())
                            // text between the end of this token and the end of the text
                            : text.substring(token.getEndOffset()));
        }
    }

    /**
     * Adds the token to the line container view with custom colors
     * 
     * @param token token to be added to the line container view
     */
    private void addTokenToLineContainer(Token token) {
        getChildren().add(createText(token.getContent(),
                switch (token.getType()) {
                    case NUMBER_LITERAL:
                        yield ColorHelperKt.generateColor(215, 199, 129);
                    case STRING_LITERAL:
                        yield ColorHelperKt.generateColor(255, 127, 108);
                    case KEYWORD:
                        yield ColorHelperKt.generateColor(249, 122, 176);
                    case COMMENT:
                        yield ColorHelperKt.generateColor(122, 126, 133);
                    default:
                        yield ColorHelperKt.generateColor(187, 189, 180);
                })
        );
    }

    /**
     * Adds a string to the lineContainer view
     * 
     * @param string string to be added to the lineContainer view
     */
    private void addStringToLineContainer(final String string) {
        getChildren().add(createText(string, ColorHelperKt.generateColor(223, 223, 223)));
    }

    /**
     * Creates a text object with custom parameters
     * 
     * @param content string content of the text object
     * @param color   color of the text
     * @return the text object
     */
    private static Text createText(final String content, final Color color) {
        // Create the text object with some sensible defaults
        // TODO: Should be controlled by passing in settings.
        var t = new Text(content);
        t.setFont(Font.font("JetBrains Mono", FontWeight.SEMI_BOLD, 15));
        t.setFill(color);
        t.setFontSmoothingType(FontSmoothingType.LCD);

        // This part should probably not be in this because its a "component"
        // but im guessing it should be fine cuz can just remove the cli functionality
        if(!GlobalConstants.Companion.getDevMode()) return t;

        Tooltip tTip = new Tooltip(
                "Content: \"" + content + "\"\n" +
                        "Color: " + ColorHelperKt.getReadableColorString(color)
        );

        tTip.setShowDelay(Duration.ZERO);
        Tooltip.install(t, tTip);

        return t;
    }

    @Override
    public void logImpl() {
        System.out.println("Line " + lineNumber);
        System.out.println("Text: \n" + (text.isEmpty() ? "EMPTY" : text)); // Just for convenience’s sake
        System.out.println("Tokens: " + tokens);
        System.out.println("====================================================");
    }
}
