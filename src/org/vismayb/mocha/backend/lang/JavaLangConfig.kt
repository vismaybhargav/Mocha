package org.vismayb.mocha.backend.lang

import org.vismayb.mocha.backend.util.RegexHelper
import java.util.regex.Pattern

class JavaLangConfig {
    companion object {
        val keywords: Array<String> = arrayOf(
            "abstract", "assert", "boolean", "break", "case",
            "catch", "class", "const", "continue", "default", "do",
            "else", "enum", "extends", "false", "final", "finally",
            "for", "goto", "if", "implements", "import",
            "instanceof", "interface", "native", "new",
            "package", "private", "protected", "public", "return",
            "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "volatile",
            "while"
        )

        val primitiveTypes: Array<String> = arrayOf(
            "void", "int", "double", "short", "long", "byte", "char"
        )

        val instanceModifers: Array<String> = arrayOf(
            "this", "super"
        )

        val keywordPattern: Pattern = RegexHelper.generateKeywordPattern(keywords)
        val singleCommentPattern: Pattern = Pattern.compile("//[^\n]*")
        val multiCommentPattern: Pattern = Pattern.compile("/\\*[\\s\\S]*?\\*/\n")
        val numberPattern: Pattern = Pattern.compile("-?\\d+(\\.\\d+)?\n")
    }
}