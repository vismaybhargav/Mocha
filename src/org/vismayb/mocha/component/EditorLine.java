package org.vismayb.mocha.component;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.vismayb.mocha.backend.lang.JavaLangConfig;
import org.vismayb.mocha.backend.token.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorLine extends HBox {
    private final String text;
    private final List<Token> tokens;

    public EditorLine(final String text, final int lineNumber) {
        this.text = text;
        tokens = new ArrayList<>();

        tokenizeString();
        generateView();
    }

    // TODO: Move over to the backend at some point

    public void tokenizeString() {
        // TODO: Add separation for primitive types.

        matchAllTokens(JavaLangConfig.Companion.getKeywordPattern(), Token.TokenType.KEYWORD);
        matchAllTokens(JavaLangConfig.Companion.getNumberPattern(), Token.TokenType.NUMBER_LITERAL);
    }

    private void matchAllTokens(final Pattern pattern, final Token.TokenType tokenType) {
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()) {
            tokens.add(new Token(matcher.start(), matcher.end(), matcher.group(), tokenType));
        }
    }

    private void sortTokensWithPriority() {
        Collections.sort(tokens);
        tokens.sort(Comparator.comparingInt(this::getTokenPriority)
                .thenComparing(Token::getStartOffset)
        );
    }

    private int getTokenPriority(final Token token) {
        return switch(token.getType()) {
            case Token.TokenType.COMMENT -> 0;
            case Token.TokenType.STRING_LITERAL -> 1;
            case Token.TokenType.NUMBER_LITERAL -> 2;
            case Token.TokenType.KEYWORD -> 3;
        };
    }

    private void generateView() {
        //sortTokensWithPriority(); // To get a sequential list of all the tokens as they appear in the file
        Collections.sort(tokens);

        // TODO:  Move the line numbers outside of the Editor into its own separate component.
        //getChildren().add(createText(Integer.toString(lineNumber), null));

        // We need to add the first bit before the first token in case the first token.startOffset() != 0
        if(!tokens.isEmpty()) {
            addStringToLineContainer(text.substring(0, tokens.getFirst().getStartOffset()));
        } else {
            // We need to just make it with the text if there are no tokens on a given line.
            addStringToLineContainer(text);
            return;
        }

        System.out.println(tokens);

        // Insert each token's contents in between the last token and the curr token.
        for(var i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);

            addTokenToLineContainer(token);

            if(i < tokens.size() - 1) {
                addStringToLineContainer(text.substring(token.getEndOffset(), tokens.get(i + 1).getStartOffset()));
            } else {
                addStringToLineContainer(text.substring(token.getEndOffset()));
            }
            /*
            addStringToLineContainer(
                    i < tokens.size() - 1
                    ? text.substring(token.getEndOffset() + 1, tokens.get(i + 1).getStartOffset())
                    : text.substring(token.getEndOffset())
            );
             */
        }
    }

    private void addTokenToLineContainer(Token token) {
        getChildren().add(createText(token.getContent(),
                switch(token.getType()) {
                    case NUMBER_LITERAL:
                        yield Color.color((double) 36 / 256, (double) 91 / 256, (double) 234 / 256);
                    case STRING_LITERAL:
                        yield Color.GREEN;
                    case KEYWORD:
                        yield Color.color((double) 6 / 256, (double) 56 / 256, (double) 181 / 256);
                    default:
                        yield Color.color((double) 17 / 256, (double) 17 / 256, (double) 17 / 256);
                }));
    }

    private void addStringToLineContainer(String string) {
        getChildren().add(createText(string, Color.BLACK));
    }

    private static Text createText(final String content, final Color color) {
        var t = new Text(content);
        t.setFont(Font.font("JetBrains Mono", FontWeight.SEMI_BOLD, 20));
        t.setFill(color);
        return t;
    }
}
