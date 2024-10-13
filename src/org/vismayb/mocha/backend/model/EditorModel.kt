package org.vismayb.mocha.backend.model

import org.vismayb.mocha.backend.polyglot.lang.keywordPattern
import org.vismayb.mocha.backend.polyglot.lang.numberPattern
import org.vismayb.mocha.backend.polyglot.lang.singleCommentPattern
import org.vismayb.mocha.backend.polyglot.lang.stringPattern

import org.vismayb.mocha.backend.token.Token

import java.io.File
import java.util.*
import java.util.regex.Pattern

class EditorModel(private val file: File) {
    val id: String = UUID.randomUUID().toString()
    private var tokens: MutableList<Token> = mutableListOf()
    private var text: String = file.useLines { it.joinToString("\n") } // Does this even work?
    var lines: List<String> = file.readLines() // Use a more robust approach

    init {
        tokenizeFile()
        tokens.sort()
        filterContainedTokensByPriority()
    }

    /**
     * Tokenizes the entire file and adds to the list of tokens
     */
    private fun tokenizeFile(){
        // TODO: Add separation for primitive types

        var i = 0
        file.forEachLine {
            matchAllTokens(it, keywordPattern,       Token.TokenType.KEYWORD,        i)
            matchAllTokens(it, numberPattern,        Token.TokenType.NUMBER_LITERAL, i)
            matchAllTokens(it, singleCommentPattern, Token.TokenType.COMMENT,        i)
            matchAllTokens(it, stringPattern,        Token.TokenType.STRING_LITERAL, i)
            i++
        }
    }

    /**
     * Matches all the tokens based on the pattern and tokenType. (Adds to the token array)
     * @param pattern Pattern to match
     * @param tokenType type of the token that is being matched
     * @param text text to match
     */
    private fun matchAllTokens(text: String, pattern: Pattern, tokenType: Token.TokenType, lineNumber: Int) {
        val matcher = pattern.matcher(text)

        while(matcher.find()) {
            tokens.add(Token(matcher.start(), matcher.end(), matcher.group(), tokenType, lineNumber))
        }
    }


    /**
     * Removes tokens from the token list if they are contained within a higher priority token
     */
    private fun filterContainedTokensByPriority() {
        // get all the high priority tokens (string and comment)
        val highPriorityIdxes = getTokenIndexesByType(Token.TokenType.COMMENT)

        highPriorityIdxes.addAll( // What are we doing anymore??
            getTokenIndexesByType(Token.TokenType.STRING_LITERAL)
        )

        val sortedIndices = highPriorityIdxes.sortedDescending()

        // Remove those tokens if they are contained within the higher priority token
        sortedIndices.forEach { idx ->
            tokens.removeIf { token ->
                token.type.getTypePriority() < Token.TokenType.STRING_LITERAL.getTypePriority()
                        && token.isContainedWithin(tokens[idx])
            }
        }
    }

    /**
     * Get the indexes of all the tokens in the tokens list that have the specified type
     * @param type The type of token to fetch
     * @return list of all the indices of the tokens with specified type
     */
    private fun getTokenIndexesByType(type: Token.TokenType): MutableList<Int> {
        val typeTokens = mutableListOf<Int>()

        for(i in tokens.indices) {
            if(tokens[i].type == type) {
                typeTokens.add(i)
            }
        }

        return typeTokens
    }

    fun getTokensByLineNumber(lineNumber: Int): List<Token> {
        return tokens.filter { it.lineNumber == lineNumber }
    }
}
