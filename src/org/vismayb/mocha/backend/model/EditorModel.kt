package org.vismayb.mocha.backend.model

import org.vismayb.mocha.backend.lang.keywordPattern
import org.vismayb.mocha.backend.lang.numberPattern
import org.vismayb.mocha.backend.lang.singleCommentPattern

import org.vismayb.mocha.backend.token.Token

import java.io.File
import java.util.*
import java.util.regex.Pattern

class EditorModel(val file: File) {
    val id: String = UUID.randomUUID().toString()
    private var tokens: MutableList<Token> = mutableListOf()
    private var text: String = file.readText()

    init {
        tokenizeFile()
    }

    /**
     * Tokenizes the entire file and adds to the list of tokens
     */
    private fun tokenizeFile(){
        // TODO: Add separation for primitive types
        matchAllTokens(keywordPattern, Token.TokenType.KEYWORD)
        matchAllTokens(numberPattern, Token.TokenType.NUMBER_LITERAL)
        matchAllTokens(singleCommentPattern, Token.TokenType.COMMENT)
    }


    /**
     * Matches all the tokens based on the pattern and tokenType. (Adds to the token array)
     * @param pattern Pattern to match
     * @param tokenType type of the token that is being matched
     */
    private fun matchAllTokens(pattern: Pattern, tokenType: Token.TokenType) {
        val matcher = pattern.matcher(text)

        while(matcher.find()) {
            tokens.add(Token(matcher.start(), matcher.end(), matcher.group(), tokenType))
        }
    }


    /**
     * Removes tokens from the token list if they are contained within a higher priority token
     */
    private fun filterContainedTokensByPriority() {
        // get all the high priority tokens (string and comment)
        val highPriorityIdxes = mutableListOf(getTokenIndexesByType(Token.TokenType.COMMENT))
        highPriorityIdxes.addAll( // What are we doing anymore??
            mutableListOf(getTokenIndexesByType(
                Token.TokenType.STRING_LITERAL
            ))
        )

        // Remove those tokens if they are contained within the higher priority token
        for (i in tokens.indices) {
            for (j in highPriorityIdxes) {
                if (tokens.get(i).isContained(tokens.get(i))) tokens.removeAt(i)
            }
        }
    }

    /**
     * Get the indexes of all the tokens in the tokens list that have the specified type
     * @param type The type of token to fetch
     * @return list of all the indices of the tokens with specified type
     */
    private fun getTokenIndexesByType(type: Token.TokenType): List<Int> {
        val typeTokens = mutableListOf<Int>()

        for(i in tokens.indices) {
            if(tokens[i].type == type) {
                typeTokens.add(i)
            }
        }

        return typeTokens
    }

    /**
     * Get the priority of a token based on its type
     * @param token the token to get the priority of
     * @return the priority level (0, high -> 3, low)
     */
    private fun getTokenPriority(token: Token): Int {
        return when(token.type) {
            Token.TokenType.COMMENT -> 0
            Token.TokenType.NUMBER_LITERAL -> 1
            Token.TokenType.STRING_LITERAL -> 2
            Token.TokenType.KEYWORD -> 3
        }
    }
}