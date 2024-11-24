package org.vismayb.mocha.view.component;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.vismayb.mocha.GlobalConstants;
import org.vismayb.mocha.backend.polyglot.lang.JavaLangConfigKt;
import org.vismayb.mocha.logging.Loggable;
import org.vismayb.mocha.backend.token.Token;
import org.vismayb.mocha.view.util.ColorHelperKt;

import java.util.List;

public class EditorLine extends HBox implements Loggable {
    public static final StringBuilder logFileBuilder = new StringBuilder();
    private final String text;
    private final List<Token> tokens;
    private final int lineNumber;

    public EditorLine(final String text, final List<Token> tokens, final int lineNumber) {
        this.text = text;
        this.tokens = tokens;
        this.lineNumber = lineNumber;

        log();
        generateView();

    }

    /**
     * Generates the view for this editorLine
     */
    private void generateView() {
        // We need to just make it with the text if there are no tokens on a given line.
        if(tokens.isEmpty()) {
            addStringToLineContainer(text);
            return;
        }

        // We need to add the first bit before the first token in case the first token.startOffset() != 0
        addStringToLineContainer(text.substring(0, tokens.getFirst().getStartOffset()));

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
                    case CALL, CLASS, NUMBER_LITERAL, STRING_LITERAL, KEYWORD, COMMENT:
                        yield JavaLangConfigKt.getTheme().get(token.getType());
                    default:
                        yield ColorHelperKt.generateColor(0, 189, 0); // For debugging new highlights
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
        if(!GlobalConstants.Companion.isDevMode()) return t;

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
        logFileBuilder.setLength(0); // Clear the string builder

        // I know this is bad formatting... what am I supposed to do??
        logFileBuilder
                .append("Line ").append(lineNumber).append("\n")
                .append("Text: \n").append(text.isEmpty() ? "EMPTY" : text).append("\n")
                .append("Tokens: \n").append(tokens).append("\n")
                .append("====================================================");
    }
}
