package org.vismayb.mocha.backend.token

import org.vismayb.mocha.backend.util.FileUtil
import java.io.File

/**
 * A piece of code that has extra data associated with it
 */
data class Token (
    val startOffset: Int,
    val endOffset: Int,
    val content: String,
    val type: TokenType,
    val lineNumber: Int
) : Comparable<Token> {

    /** EACH CASE IN THE ENUM HAS TO BE PLACED IN ORDER OF THEIR PRIORITY */
    enum class TokenType {
        MISC, // Just any text that is none of the stuff at the top
        KEYWORD, // Determined by polyglot language config
        NUMBER_LITERAL, // Integers, Decimal
        STRING_LITERAL, // Double Quotes
        COMMENT; // Single and Multiline
        /**
         * Get the priority of a token based on its type
         * @param token the token to get the priority of
         * @return the priority level (0, high -> 3, low)
         */
        fun getTypePriority(): Int {
            return ordinal
        }
    }

    /**
     * Checks if a token is contained within another one.
     * @param other the token that might contain this token
     * @return true if token1 is contained by the other token
     */
    fun isContainedWithin(other: Token): Boolean = (lineNumber == other.lineNumber && startOffset >= other.startOffset && endOffset <= other.endOffset)

    /**
     * compareTo implementation for the token class.
     * @param other the other token
     * @return the compareTo value
     */
    override fun compareTo(other: Token): Int = startOffset.compareTo(other.startOffset)
}

