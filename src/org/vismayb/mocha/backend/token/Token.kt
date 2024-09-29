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
    val type: TokenType
) : Comparable<Token> {

    /** EACH CASE IN THE ENUM HAS TO BE PLACED IN ORDER OF THEIR PRIORITY */
    enum class TokenType {
        COMMENT, // Single and Multiline
        NUMBER_LITERAL, // Integers, Decimal
        STRING_LITERAL, // Double Quotes
        KEYWORD, // Determined by polyglot language config
        MISC; // Just any text that is none of the stuff at the top

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
     * Gets the lineNumber of the file that this token is on.
     * @param file the file that this token is inside
     */
    fun getLineNumber(file: File?): Int {
        val lines: List<String> = FileUtil.getAllLines(file)

        var currentOffset = 0
        var lineNumber = 1

        for (line in lines) {
            val lineLength = line.length + 1 // Include the "\n"

            if (currentOffset + lineLength > endOffset) {
                return lineNumber
            }

            currentOffset += lineLength
            lineNumber++
        }

        return -1 // Never found ig?
    }

    /**
     * Checks if a token is contained within another one.
     * @param other the token that might contain this token
     * @return true if token1 is contained by the other token
     */
    fun isContainedWithin(other: Token): Boolean = (startOffset >= other.startOffset && endOffset <= other.endOffset)

    /**
     * compareTo implementation for the token class.
     * @param other the other token
     * @return the compareTo value
     */
    override fun compareTo(other: Token): Int = startOffset.compareTo(other.startOffset)
}

