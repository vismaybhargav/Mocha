package org.vismayb.mocha.backend.token

import org.vismayb.mocha.backend.util.FileUtil
import java.io.File

data class Token(val startOffset: Int, val endOffset: Int, val content: String, val type: TokenType) :
    Comparable<Token> {
    enum class TokenType {
        COMMENT,
        NUMBER_LITERAL,
        STRING_LITERAL,
        KEYWORD,
    }

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

    fun isContained(other: Token): Boolean = (startOffset >= other.startOffset && endOffset <= other.endOffset)

    override fun compareTo(other: Token): Int {
        return startOffset - other.startOffset
    }
}

