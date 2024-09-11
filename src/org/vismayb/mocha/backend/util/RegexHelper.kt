package org.vismayb.mocha.backend.util

import java.util.regex.Pattern

open class RegexHelper {
    object JavaLangConfig : RegexHelper() {
        var keywords: Array<String> = arrayOf(
            "abstract", "assert", "boolean", "break", "byte", "case",
            "catch", "char", "class", "const", "continue", "default", "do",
            "double", "else", "enum", "extends", "false", "final", "finally",
            "float", "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native", "new",
            "package", "private", "protected", "public", "return", "short",
            "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile",
            "while"
        )
    }

    companion object {
        fun generateKeywordPattern(keywords: Array<String>): Pattern {
            return Pattern.compile("\\b(" + keywords.joinToString("|")  + ")\\b")
        }
    }
}
