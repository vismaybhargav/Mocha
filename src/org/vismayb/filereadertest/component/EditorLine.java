package org.vismayb.filereadertest.component;

import javafx.scene.layout.HBox;
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

        tokenizeString(text);
        generateView();
    }

    // TODO: Move over to the backend at some point
    public void tokenizeString(final String str) {
        // TODO: Separate the primitive types and the keywords
        Pattern keywordRegex = Pattern.compile(
                "\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const" +
                        "|continue|default|do|double|else|enum|extends|final|finally" +
                        "|float|for|goto|if|implements|import|instanceof|int|interface" +
                        "|long|native|new|null|package|private|protected|public|return" +
                        "|short|static|strictfp|super|switch|synchronized|this|throw|throws" +
                        "|transient|try|void|volatile|while)\\b"
        );

        Pattern numberRegex = Pattern.compile("\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?[fFdD]?\\b\n");

        Pattern whiteSpaceRegex = Pattern.compile("\\s+");

        Matcher matcher = keywordRegex.matcher(str);

        // We add the tokens to the entire list because we need to sort the list to add to the
        // editorLine as a sequential list of tokens.

        //==----- ALL Regex Matching -----==\\
        while(matcher.find()) {
            tokens.add(new Token(matcher.start(), matcher.end(), matcher.group(), Token.TokenType.KEYWORD));
        }

        /*
        matcher = numberRegex.matcher(str);

        while(matcher.find()) {
            tokens.add(new Token(matcher.start(), matcher.end(), matcher.group(), Token.TokenType.INTEGER_LITERAL));
        }
         */

        /*
        matcher = whiteSpaceRegex.matcher(str);

        while(matcher.find()) {
            tokens.add(new Token(matcher.start(), matcher.end(), matcher.group(), Token.TokenType.WHITESPACE));
        }
         */
    }

    // TODO: Refactor so that we aren't building the view based on the tokens giving but rather
    // COLORING the view using the token offsets!!!! This would mean that we still have the syntax highlighting
    // but we dont need to worry about weird regex-ing!!. Holy im so genius
    private void generateView() {
        Collections.sort(tokens); //To get a sequential list of all the tokens as they appear in the file

        // Line Number
        getChildren().add(createText(Integer.toString(lineNumber)));


        for(var i = 0; i < tokens.size() - 1; i++) {
            // Insert each token's contents in between the last token and the curr token.
            var token = tokens.get(i);
            getChildren().add(createText(
                    token.content() + text.substring(token.endOffset() + 1, tokens.get(i + 1).startOffset())
            ));
        }
    }

    private static Text createText(final String content) {
        Text t = new Text(content);
        t.setFont(Font.font("SF Mono", 20));
        return t;
    }
}
