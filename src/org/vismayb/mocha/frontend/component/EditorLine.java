package org.vismayb.mocha.frontend.component;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.vismayb.mocha.backend.lang.JavaLangConfigKt;
import org.vismayb.mocha.backend.token.Token;
import org.vismayb.mocha.backend.util.ColorHelperKt;

import java.util.ArrayList;
import java.util.Collections;
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
        matchAllTokens(JavaLangConfigKt.getKeywordPattern(), Token.TokenType.KEYWORD);
        matchAllTokens(JavaLangConfigKt.getNumberPattern(),  Token.TokenType.NUMBER_LITERAL);
        matchAllTokens(JavaLangConfigKt.getSingleCommentPattern(), Token.TokenType.COMMENT);
    }

    private void matchAllTokens(final Pattern pattern, final Token.TokenType tokenType) {
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()) {
            tokens.add(new Token(matcher.start(), matcher.end(), matcher.group(), tokenType));
        }
    }

    private void filterContainedTokensByPriority() {
        // Basically the string and comment tokens
        var highPriorityTokenIdxes = getTokenIndexesByType(Token.TokenType.COMMENT);
        highPriorityTokenIdxes.addAll(getTokenIndexesByType(Token.TokenType.STRING_LITERAL));

        // Remove the tokens if it is contained within a higherPriorityToken
        // TODO: O(n^2) right now, check if we can make it faster
        for(int i = 0; i < tokens.size(); i++) {
            for (Integer highPriorityTokenIdx : highPriorityTokenIdxes) {
                if (isContained(tokens.get(i), tokens.get(highPriorityTokenIdx))) {
                    tokens.remove(i);
                }
            }
        }
    }

    private List<Integer> getTokenIndexesByType(Token.TokenType type) {
        var typeTokens = new ArrayList<Integer>();

        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType() == type) {
                typeTokens.add(i);
            }
        }

        return typeTokens;
    }

    /**
     * Checks if a token is contained within another one.
     * @param token1 the token that might be contained inside the other
     * @param token2 the token that might contain the other token
     * @return {@code true} if token1 is contained by token2
     */
    private boolean isContained(Token token1, Token token2) {
        return (token1.getStartOffset() >= token2.getStartOffset()
                && token1.getEndOffset() <= token2.getEndOffset());
    }

    /**
     * Get the priority of token based on its {@link org.vismayb.mocha.backend.token.Token.TokenType TokenType}
     * @param token token to check the priority of
     * @return the priority of the token [0 (high) -> 4 (low)].
     */
    private int getTokenPriority(final Token token) {
        return switch(token.getType()) {
            case Token.TokenType.COMMENT -> 0;
            case Token.TokenType.STRING_LITERAL -> 1;
            case Token.TokenType.NUMBER_LITERAL -> 2;
            case Token.TokenType.KEYWORD -> 3;
        };
    }

    /**
     * Generates the view for this editorLine
     */
    private void generateView() {
        //sortTokensWithPriority(); // To get a sequential list of all the tokens as they appear in the file

        filterContainedTokensByPriority();
        Collections.sort(tokens);

        // TODO:  Move the line numbers outside of the Editor into its own separate component.
        //getChildren().add(createText(Integer.toString(lineNumber), null));

        System.out.println("" + tokens);

        // We need to add the first bit before the first token in case the first token.startOffset() != 0
        if(!tokens.isEmpty()) {
            addStringToLineContainer(text.substring(0, tokens.getFirst().getStartOffset()));
        } else {
            // We need to just make it with the text if there are no tokens on a given line.
            addStringToLineContainer(text);
            return;
        }

        // Insert each token's contents in between the last token and the curr token.
        for(var i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);

            addTokenToLineContainer(token);

            addStringToLineContainer(
                    i < tokens.size() - 1
                            // text between the end of this token and the start of the next one
                            ? text.substring(token.getEndOffset(), tokens.get(i + 1).getStartOffset())
                            // text between the end of this token and the end of the text
                            : text.substring(token.getEndOffset())
            );
        }
    }

    /**
     * Adds the token to the line container view with custom colors
     * @param token token to be added to the line container view
     */
    private void addTokenToLineContainer(Token token) {
        getChildren().add(createText(token.getContent(),
                switch(token.getType()) {
                    case NUMBER_LITERAL:
                        yield ColorHelperKt.generateColor(36, 91, 234);
                    case STRING_LITERAL:
                        yield Color.GREEN;
                    case KEYWORD:
                        yield ColorHelperKt.generateColor(6, 56, 181);
                    default:
                        yield ColorHelperKt.generateColor(187, 189, 180);
                })
        );
    }

    /**
     * Adds a string to the lineContainer view
     * @param string string to be added to the lineContainer view
     */
    private void addStringToLineContainer(final String string) {
        getChildren().add(createText(string, ColorHelperKt.generateColor(187, 189, 180)));
    }

    /**
     * Creates a text object with custom parameters
     * @param content string content of the text object
     * @param color color of the text
     * @return the text object
     */
    private static Text createText(final String content, final Color color) {
        var t = new Text(content);
        t.setFont(Font.font("JetBrains Mono", FontWeight.SEMI_BOLD, 20));
        t.setFill(color);
        return t;
    }
}
