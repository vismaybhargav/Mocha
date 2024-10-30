package org.vismayb.mocha.backend.polyglot.lang

import org.vismayb.mocha.backend.util.generateKeywordPattern
import java.util.regex.Pattern

private val keywords: Array<String> = arrayOf(
    "abstract", "assert", "boolean", "break", "case",
    "catch", "class", "const", "continue", "default", "do",
    "else", "enum", "extends", "false", "final", "finally",
    "for", "goto", "if", "implements", "import",
    "instanceof", "interface", "native", "new",
    "package", "private", "protected", "public", "return",
    "static", "strictfp", "super", "switch", "synchronized", "this",
    "throw", "throws", "transient", "try", "volatile",
    "while", "var"
)

val primitiveTypes: Array<String> = arrayOf(
    "void", "int", "double", "short", "long", "byte", "char"
)

val instanceModifiers: Array<String> = arrayOf(
    "this", "super"
)

val keywordPattern: Pattern = generateKeywordPattern(keywords.plus(primitiveTypes).plus(instanceModifiers))
val singleCommentPattern: Pattern = Pattern.compile("//[^\n]*")
val multiCommentPattern: Pattern = Pattern.compile("/\\*[\\s\\S]*?\\*/\n")

val stringPattern: Pattern = Pattern.compile("\"(?:\\\\.|[^\"\\\\])*\"");

/**
 * Works with +/-, f/F, l/L, decimals and integers. OPEN-AI o1 is SO SMART!!
 */
val numberPattern: Pattern = Pattern.compile("([-]?(\\d+(\\.\\d+)?|\\.\\d+)[FfLl]?)")
