package org.vismayb.filereadertest.backend.token;

import org.vismayb.filereadertest.FileUtil;

import java.io.File;
import java.util.List;

public record Token(int startOffset, int endOffset, String content, TokenType type) implements Comparable<Token> {
    public enum TokenType {
        INTEGER_LITERAL,
        STRING_LITERAL,
        DECIMAL_LITERAL,
        KEYWORD,
        OPERATOR,
        WHITESPACE,
        MISC
    }

    public int getLineNumber(File file) {
        List<String> lines = FileUtil.getAllLines(file);

        int currentOffset = 0;
        int lineNumber = 1;

        for(String line : lines) {
            int lineLength = line.length() + 1; // Include the "\n"

            if(currentOffset + lineLength > endOffset) {
                return lineNumber;
            }

            currentOffset += lineLength;
            lineNumber++;
        }

        return -1; // Never found ig?
    }

    @Override
    public int compareTo(Token o) {
        return startOffset() - o.startOffset();
    }
}

