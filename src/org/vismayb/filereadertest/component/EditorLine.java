package org.vismayb.filereadertest.component;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.vismayb.filereadertest.backend.token.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorLine extends HBox {
    private int lineNumber;
    private String text;
    private List<Token> tokens;

    public EditorLine(final String text, final int lineNumber) {
        this.lineNumber = lineNumber;
        this.text = text;
        tokens = new ArrayList<>();

        tokenizeString();
        generateView();
    }

    // TODO: Move over to the backend at some point
    public void tokenizeString() {
        // TODO: Add separation for primitive types.
        Pattern keywordRegex = Pattern.compile(
                "\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b"
        );

        // keyword Matcher
        Matcher matcher = keywordRegex.matcher(text);
        while(matcher.find()) {
            tokens.add(new Token(matcher.start(), matcher.end(), matcher.group(), Token.TokenType.KEYWORD));
        }
    }

    private void generateView() {
        Collections.sort(tokens); // To get a sequential list of all the tokens as they appear in the file

        // TODO:  Move the line numbers outside of the Editor into its own separate component.
        //getChildren().add(createText(Integer.toString(lineNumber), null));

        // We need to add the first bit before the first token in case the first token.startOffset() != 0
        if(!tokens.isEmpty()) {
            getChildren().add(createText(text.substring(0, tokens.getFirst().startOffset()), null));
        } else {
            // We need to just make it with the text if there are no tokens on a given line.
            getChildren().add(createText(text, null));
            return;
        }

        // Insert each token's contents in between the last token and the curr token.
        for(var i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);

            if(i < tokens.size() - 1) {
                getChildren().add(createText(token.content(), Color.RED));
                getChildren().add(createText(text.substring(token.endOffset(), tokens.get(i + 1).startOffset()), null));
            } else {
                getChildren().add(createText(token.content(), Color.RED));
                getChildren().add(createText(text.substring(token.endOffset()), null));
            }
        }
    }

    private static Text createText(final String content, final Color color) {
        var t = new Text(content);
        t.setFont(Font.font("SF Mono", 20));
        if(color !=  null)
            t.setFill(color);
        return t;
    }
}
