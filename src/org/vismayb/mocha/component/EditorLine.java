package org.vismayb.mocha.component;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.vismayb.mocha.backend.lang.JavaLangConfig;
import org.vismayb.mocha.backend.token.Token;
import org.vismayb.mocha.backend.util.RegexHelper;

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

        // keyword Matcher
        Matcher matcher = keywordRegex.matcher(text);
        matchAllTokens(JavaLangConfig.Companion.getKeywordPattern(), Token.TokenType.KEYWORD);
        matchAllTokens(JavaLangConfig.Companion.getNumberPattern(), Token.TokenType.NUMBER_LITERAL);
    }

    private void matchAllTokens(final Pattern pattern, final Token.TokenType tokenType) {
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()) {
            tokens.add(new Token(matcher.start(), matcher.end(), matcher.group(), tokenType));
        }
    }

    private void generateView() {
        Collections.sort(tokens); // To get a sequential list of all the tokens as they appear in the file

        // TODO:  Move the line numbers outside of the Editor into its own separate component.
        //getChildren().add(createText(Integer.toString(lineNumber), null));

        // We need to add the first bit before the first token in case the first token.startOffset() != 0
        if(!tokens.isEmpty()) {
            getChildren().add(createText(text.substring(0, tokens.getFirst().getStartOffset()), null));
        } else {
            // We need to just make it with the text if there are no tokens on a given line.
            getChildren().add(createText(text, null));
            return;
        }

        // Insert each token's contents in between the last token and the curr token.
        for(var i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);

            if(i < tokens.size() - 1) {
                getChildren().add(createText(token.getContent(), Color.RED));
                getChildren().add(createText(text.substring(token.getEndOffset(), tokens.get(i + 1).getStartOffset()), null));
            } else {
                getChildren().add(createText(token.getContent(), Color.RED));
                getChildren().add(createText(text.substring(token.getEndOffset()), null));
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
